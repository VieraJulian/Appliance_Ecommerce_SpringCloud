package com.ApplianceEcommerce.sales.service;

import com.ApplianceEcommerce.sales.dto.SaleDTO;

public interface ISaleService {

    public SaleDTO getSale(Long id);

    public SaleDTO getSaleByOperationCode(String operationCode);

    public SaleDTO createSale(Long cart_id);

    public void deleteSale(Long id);
}
