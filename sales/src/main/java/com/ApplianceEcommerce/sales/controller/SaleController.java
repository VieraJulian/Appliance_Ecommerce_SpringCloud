package com.ApplianceEcommerce.sales.controller;

import com.ApplianceEcommerce.sales.dto.SaleDTO;
import com.ApplianceEcommerce.sales.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private ISaleService saleServ;

    @GetMapping("/detail/{id}")
    public ResponseEntity<SaleDTO> getSale(@PathVariable Long id) {
        try {
            SaleDTO sale = saleServ.getSale(id);

            return new ResponseEntity<>(sale, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<SaleDTO> getSaleByOperationCode(@RequestParam String operationCode) {
        try {
            SaleDTO sale = saleServ.getSaleByOperationCode(operationCode);

            return new ResponseEntity<>(sale, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<SaleDTO> createSale(@RequestParam Long cart_id) {
        try {
            SaleDTO sale = saleServ.createSale(cart_id);

            return new ResponseEntity<>(sale, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable Long id) {
        try {
            saleServ.deleteSale(id);

            return new ResponseEntity<>("¡Sale successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
