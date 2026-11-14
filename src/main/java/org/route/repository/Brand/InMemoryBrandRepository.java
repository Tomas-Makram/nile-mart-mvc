package org.route.repository.Brand;

import jakarta.annotation.PostConstruct;
import org.route.model.Brand;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryBrandRepository implements BrandRepository {
    List<Brand> brand = new ArrayList<>();

    // Create Data After Create Object From Class (database)
    @PostConstruct
    void init() {
        brand.add(Brand.builder().brandName("Samsung").slug("samsung").build());
        brand.add(Brand.builder().brandName("Asus").slug("asus").build());
        brand.add(Brand.builder().brandName("Anker").slug("anker").build());
        brand.add(Brand.builder().brandName("Tefal").slug("tefal").build());
        brand.add(Brand.builder().brandName("Old Brand").slug("old-brand").active(false).build());
    }

    // Return all array(database)
    @Override
    public List<Brand> findAll() {
        return brand;
    }

    // Return brand while Slag inside array(database)
    @Override
    public Brand findBySlug(String slug) {
        return brand.stream()
                .filter(b -> b.getSlug().equalsIgnoreCase(slug.trim()))
                .findFirst()
                .orElse(null);
    }

    // Add new item from brand in array(database)
    @Override
    public void save(Brand newBrand) {
        brand.add(newBrand);
    }

}