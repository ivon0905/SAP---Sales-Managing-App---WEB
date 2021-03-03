package com.example.demo.service;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<Product> listAll() {
        return repo.findAll();
    }

    public void save(Product product) {
        repo.save(product);
    }

    public Product get(int id) {
        return repo.findById(id).get();
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public boolean getCode(int code){
        List<Product> list = listAll();
        if(list.contains(code))
            return true;
        return false;
    }

    public int setQuantity(int quantity, int code){
         Product product = repo.findByProductCode(code);
         int newQuantity = quantity + product.getQuantity();
         repo.deleteById(product.getId());
         return newQuantity;
    }
}
