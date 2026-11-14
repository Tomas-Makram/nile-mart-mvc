package org.route.repository.Product;

import jakarta.annotation.PostConstruct;
import org.route.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Repository
public class InMemoryProductRepository implements ProductRepository {
    List<Product> myProducts = new ArrayList<>();

    // Create array with data after Create object from class (create database)
    @PostConstruct
    void init() {
        myProducts.add(Product.builder()
                .sku("P002")
                .name("Desk Lamp")
                .price(BigDecimal.valueOf(500))
                .inStock(10)
                .category("lighting")
                .build());

        myProducts.add(Product.builder()
                .sku("P003")
                .name("ASUS TUF")
                .brandSlug("asus")
                .price(BigDecimal.valueOf(24000))
                .inStock(4)
                .category("laptops")
                .build());

        myProducts.add(Product.builder()
                .sku("PH-A55-128")
                .name("Samsung Galaxy A55 128GB")
                .brandSlug("samsung")
                .price(BigDecimal.valueOf(18500.00))
                .inStock(7)
                .category("mobile-phones")
                .build());
    }

    // Get all Products
    @Override
    public List<Product> findAll() {
        return myProducts;
    }

    // Get product by skuName
    @Override
    public Product findBySku(String sku) {
        return myProducts.stream()
                .filter(x -> x.getSku().equalsIgnoreCase(sku))
                .findFirst().orElse(null);
    }

    // Save product in array(database)
    @Override
    public void save(Product product) {
        myProducts.add(product);
    }
}
