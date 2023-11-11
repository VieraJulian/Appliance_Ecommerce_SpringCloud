package com.ApplianceEcommerce.sales.service;

import com.ApplianceEcommerce.sales.dto.CartDTO;
import com.ApplianceEcommerce.sales.dto.CartProductDTO;
import com.ApplianceEcommerce.sales.dto.ProductDTO;
import com.ApplianceEcommerce.sales.dto.SaleDTO;
import com.ApplianceEcommerce.sales.model.Sale;
import com.ApplianceEcommerce.sales.repository.ICartAPI;
import com.ApplianceEcommerce.sales.repository.ISalesRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class SaleService implements ISaleService {

    @Autowired
    private ISalesRepository saleRepo;

    @Autowired
    private IProductAPI productAPI;

    @Autowired
    private ICartAPI cartAPI;

    @Override
    public SaleDTO getSale(Long id) {
        Sale saleDB = saleRepo.findById(id).orElse(null);

        if (saleDB == null) {
            return null;
        }

        return SaleDTO.builder()
                .id(saleDB.getId())
                .date(saleDB.getDate())
                .operationCode(saleDB.getOperationCode())
                .total(saleDB.getTotal())
                .build();
    }

    @Override
    @CircuitBreaker(name = "carts-service", fallbackMethod = "fallbackCart")
    @Retry(name = "carts-service")
    public SaleDTO getSaleByOperationCode(String operationCode) {

        Sale saleDB = saleRepo.getSaleByOperationCode(operationCode);

        if (saleDB == null) {
            return null;
        }

        CartDTO cart = cartAPI.getCart(saleDB.getCart_id());

        return SaleDTO.builder()
                .id(saleDB.getId())
                .date(saleDB.getDate())
                .operationCode(saleDB.getOperationCode())
                .total(saleDB.getTotal())
                .build();
    }

    @Override
    @CircuitBreaker(name = "carts-service", fallbackMethod = "fallbackCart")
    @Retry(name = "carts-service")
    public SaleDTO createSale(Long cart_id) {

        CartDTO cart = cartAPI.getCart(cart_id);

        if (cart == null) {
            return null;
        }

        cartAPI.clearCart(cart_id);

        for (CartProductDTO cp : cart.getListProducts()) {
            Integer quantity = cp.getQuantity();

            productAPI.reduceStock(cp.getProduct().getId(), quantity);
        }


        Sale sale = saleRepo.save(Sale.builder()
                .cart_id(cart_id)
                .date(LocalDate.now())
                .operationCode(UUID.randomUUID().toString())
                .total(cart.getTotal())
                .build());

        return SaleDTO.builder()
                .id(sale.getId())
                .operationCode(sale.getOperationCode())
                .date(sale.getDate())
                .total(sale.getTotal())
                .build();
    }

    @Override
    public void deleteSale(Long id) {
        saleRepo.deleteById(id);
    }

    public SaleDTO fallbackCart(Throwable t) {
        System.out.println("--- Error ---: " + t.getMessage());

        return new SaleDTO();
    }

}
