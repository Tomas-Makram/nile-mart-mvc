package org.route.repository.Category;

import jakarta.annotation.PostConstruct;
import org.route.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository{
    private final List<Category> category = new ArrayList<>();

    // Create array data after create object from class (Create Database)
    @PostConstruct
    void init() {
        category.add(Category.builder()
                .categoryName("Electronics")
                .slug("electronics")
                .parentSlug(null)
                .displayOrder(1)
                .active(true).build());

        category.add(Category.builder()
                .categoryName("Home & Kitchen")
                .slug("home-kitchen")
                .parentSlug(null)
                .displayOrder(2)
                .active(true).build());

        category.add(Category.builder()
                .categoryName("Fashion")
                .slug("fashion")
                .parentSlug(null)
                .displayOrder(3)
                .active(false).build());

        category.add(Category.builder()
                .categoryName("Mobile Phones")
                .slug("mobile-phones")
                .parentSlug("electronics")
                .displayOrder(1)
                .active(true)
                .isChild(true).build());

        category.add(Category.builder()
                .categoryName("Laptops")
                .slug("laptops")
                .parentSlug("electronics")
                .displayOrder(2)
                .active(true)
                .isChild(true).build());

        category.add(Category.builder()
                .categoryName("Accessories")
                .slug("electronics-accessories")
                .parentSlug("electronics")
                .displayOrder(3)
                .active(true)
                .isChild(true).build());

        category.add(Category.builder()
                .categoryName("Cookware")
                .slug("cookware")
                .parentSlug("home-kitchen")
                .displayOrder(1)
                .active(true)
                .isChild(true).build());

        category.add(Category.builder()
                .categoryName("Lighting")
                .slug("lighting")
                .parentSlug("home-kitchen")
                .displayOrder(2)
                .active(true)
                .isChild(true).build());

        category.add(Category.builder()
                .categoryName("Men's Clothing")
                .slug("mens-clothing")
                .parentSlug("fashion")
                .displayOrder(1)
                .active(true)
                .isChild(true).build());
    }

    // Get all data from array(database)
    @Override
    public List<Category> findAll() {
        return category;
    }

    // Get Category from slug name in array
    @Override
    public Category findBySlug(String slug) {
        return category.stream()
                .filter(c -> c.getSlug().equalsIgnoreCase(slug.trim()))
                .findFirst().orElse(null);
    }

    // add new category in array(database)
    @Override
    public void save(Category newCategory) {
        category.add(newCategory);
    }
}
