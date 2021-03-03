package com.example.demo.repositories;

import com.example.demo.models.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoldProductRepository extends JpaRepository<SoldProduct, Integer> {
    @Query("SELECT b FROM SoldProduct b WHERE b.brand = ?1")
    public List<SoldProduct> findAllByBrand(String brand);

    @Query("SELECT DISTINCT a.brand FROM SoldProduct a")
    public String[] getAllBrands();
}
