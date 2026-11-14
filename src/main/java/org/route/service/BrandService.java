package org.route.service;

import org.route.model.Brand;
import org.route.repository.Brand.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    // Create Constructor
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    // Return all the brands
    public List<Brand> getAllBrand(){
        return brandRepository.findAll();
    }

    // Return all active brands
    public List<Brand> getActiveBrand(){
        return brandRepository.findAll().stream().filter(Brand::isActive).toList();
    }

    // Return brand from slug name
    public Brand findBySlug(String slug) {
        if (slug == null || slug.isBlank())
            return null;
        return brandRepository.findBySlug(slug);
    }

    // Get brandName and slugName
    public Map<String, String> getBrandNamesBySlug() {
        return brandRepository.findAll().stream().collect(Collectors
                .toMap(Brand::getSlug, Brand::getBrandName));
    }

    // Add new brand
    public void addNewBrand(Brand newBrand){
        brandRepository.save(newBrand);
    }

    // Validate Unique brandName with ignore case
    public boolean existsByBrandNameIgnoreCase(String brandName) {
        return brandRepository.findAll().stream()
                .anyMatch(b -> b.getBrandName().equalsIgnoreCase(brandName));
    }

    // Validate Unique slugName with ignore case
    public boolean existByBrandSlugIgnoreCase(String brandSlug) {
        return brandRepository.findAll().stream()
                .anyMatch(b -> b.getSlug().equalsIgnoreCase(brandSlug));
    }
}
