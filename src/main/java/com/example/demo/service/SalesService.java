package com.example.demo.service;

import com.example.demo.models.SoldProduct;
import com.example.demo.repositories.SoldProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class SalesService {

    @Autowired
    private SoldProductRepository repo;

    public List<SoldProduct> listAll(){return repo.findAll();}

    public Object[][] getProfitsByBrands() {
        List<SoldProduct> sp = listAll();
        String[] brands = repo.getAllBrands();
        int length = brands.length;
        int m=0;
        Object[][] brandsAndProfit = new Object[length][2];
        Double[] profit = new Double[length];
        for(int i=0;i<length;i++){
            profit[i]=0.0;
        }
        for(SoldProduct soldProduct : sp){
            for(int i = 0;i<length;i++){
                if(soldProduct.getBrand().equals(brands[i])) {
                    profit[i] += soldProduct.getFinalPrice();
                }
            }
        }
        for(int i=0;i<length;i++){
            brandsAndProfit[i][0] = brands[i];
            brandsAndProfit[i][1] = profit[i];
        }
        return brandsAndProfit;
    }

    public Double[] getSalesByMonths(){
        String date;
        List<SoldProduct> sp = listAll();
        Double[] sum ={0.0,0.0,0.0,0.0,0.0,0.0};
        for(SoldProduct soldProduct : sp){
            date = String.valueOf(soldProduct.getDate());
            String[] month = date.split("-");
            if(month[1].equals("09")){
                sum[0] += soldProduct.getFinalPrice();
            }
            else if(month[1].equals("10")){
                sum[1] += soldProduct.getFinalPrice();
            }
            else if(month[1].equals("11")){
                sum[2] += soldProduct.getFinalPrice();
            }
            else if(month[1].equals("12")){
                sum[3] += soldProduct.getFinalPrice();
            }
            else if(month[1].equals("01")){
                sum[4] += soldProduct.getFinalPrice();
            }
            else if(month[1].equals("02")){
                sum[5] += soldProduct.getFinalPrice();
            }
        }
        return sum;
    }

    public Double[] getSalesByBrand(String brand){
        String date;
        List<SoldProduct> sp = listAll();
        Double[] sum ={0.0,0.0,0.0,0.0,0.0,0.0};
        for(SoldProduct soldProduct : sp){
            if(soldProduct.getBrand().equals(brand)){
                date = String.valueOf(soldProduct.getDate());
                String[] month = date.split("-");
                if(month[1].equals("09")){
                    sum[0] += soldProduct.getFinalPrice();
                }
                else if(month[1].equals("10")){
                    sum[1] += soldProduct.getFinalPrice();
                }
                else if(month[1].equals("11")){
                    sum[2] += soldProduct.getFinalPrice();
                }
                else if(month[1].equals("12")){
                    sum[3] += soldProduct.getFinalPrice();
                }
                else if(month[1].equals("01")){
                    sum[4] += soldProduct.getFinalPrice();
                }
                else if(month[1].equals("02")){
                    sum[5] += soldProduct.getFinalPrice();
                }
            }
        }
        return sum;
    }
}
