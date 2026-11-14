package org.route.service;

import org.route.model.Brand;
import org.route.model.Category;
import org.route.model.Product;
import org.route.repository.Category.CategoryRepository;
import org.route.repository.Product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;

    // Create instructor
    public ProductService(ProductRepository productRepository, CategoryService categoryService, CategoryRepository categoryRepository, CategoryService categoryService1, BrandService brandService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService1;
        this.brandService = brandService;
    }

    // Get all products where stock bigger than 0
    public List<Product> getAvailableProducts() {
        return productRepository.findAll().stream()
                .filter(x-> x.getInStock() > 0)
                .toList();
    }

    // Add new product
    public void addNewProduct(Product product) {
        product.setBrandSlug(product.getBrandSlug() == null || product.getBrandSlug().isBlank()
                ? null : product.getBrandSlug().trim());
        productRepository.save(product);
    }

    // Check is squ unique
    public boolean isProductExist(String sku) {
        return productRepository.findBySku(sku) != null;
    }

    // Validate category must be a sub-category
    public boolean isCategoryValid(String slug) {
        if (slug == null || slug.isBlank())
            return true;
        Category c = categoryService.findBySlug(slug);
        return c != null && c.getParentSlug() != null;
    }

    // Validate if Brand not null and active
    public boolean isBrandValid(String slug) {
        if (slug == null || slug.isBlank())
            return true;
        Brand b = brandService.findBySlug(slug);
        return b != null && b.isActive();
    }
}
