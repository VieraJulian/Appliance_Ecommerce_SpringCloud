package com.ApplianceEcommerce.sales.repository;

import com.ApplianceEcommerce.sales.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalesRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.operationCode = :operationCode")
    public Sale getSaleByOperationCode(String operationCode);
}
