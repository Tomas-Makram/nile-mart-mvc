package org.route.service;

import org.route.model.Category;
import org.route.repository.Category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // Create Constructor
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get all category by parent (Parent Name , category Sub)
    public Map<String, List<Category>> getAllCategory() {
        List<Category> all = categoryRepository.findAll();

        // group sub-categories by parentSlug
        Map<String, List<Category>> subsByParent = all.stream()
                .filter(c -> c.getParentSlug() != null)
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .collect(Collectors.groupingBy(Category::getParentSlug));

        // Parent first then its subs
        Map<String, List<Category>> grouped = new LinkedHashMap<>();
        all.stream()
                .filter(c -> !c.isChild())
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .forEach(parent -> {
                    List<Category> group = new ArrayList<>();
                    group.add(parent);
                    group.addAll(subsByParent.getOrDefault(parent.getSlug(), List.of()));
                    grouped.put(parent.getSlug(), group);
                });

        return grouped;
    }

    // Get Category by Map <Slug, CategoryName>
    public Map<String, String> getCategoryNamesBySlug() {
        return categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getSlug, Category::getCategoryName));
    }

    // Get all top-level Categories
    public List<Category> getTopLevelCategories() {
        return categoryRepository.findAll().stream()
                .filter(c -> !c.isChild())
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .toList();
    }

    // Get all sup-level Categories
    public List<Category> getSubCategories() {
        return categoryRepository.findAll().stream()
                .filter(c -> c.getParentSlug() != null)
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .toList();
    }

    // Get all sup-level in parentSlug Categories
    public List<Category> getSubCategoriesByParent(String parentSlug) {
        return categoryRepository.findAll().stream()
                .filter(c ->c.getParentSlug() != null
                        && c.getParentSlug().equalsIgnoreCase(parentSlug))
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .toList();
    }

    // Get the names of top-level categories or the names of sub-categories from parent slug
    public List<String> getChildCategoryName(String parentSlug) {

        List<Category> categories = (parentSlug == null || parentSlug.isBlank())
                ? getTopLevelCategories()
                : getSubCategoriesByParent(parentSlug);

        return categories.stream()
                .map(Category::getCategoryName)
                .filter(Objects::nonNull)
                .toList();
    }

    // Get category by slug name
    public Category findBySlug(String slug) {
        if (slug == null || slug.isBlank()) return null;
        return categoryRepository.findBySlug(slug);
    }

    // Get category name from slug name
    public String getCategoryNameBySlug(String slug) {
        return categoryRepository.findAll().stream()
                .filter(c -> c.getSlug().equalsIgnoreCase(slug))
                .map(Category::getCategoryName)
                .findFirst().orElse(slug);
    }

    // Add new category
    public void addNewCategory(Category category) {
        boolean hasParent = category.getParentSlug() != null &&
                !category.getParentSlug().isBlank();

        category.setParentSlug(hasParent ? category.getParentSlug().trim() : null);
        category.setChild(hasParent);

        categoryRepository.save(category);
    }

    // Validate parent not null and top-level category
    public boolean validateParent(String parentSlug) {
        if (parentSlug == null || parentSlug.isBlank())
            return true;
        Category p = findBySlug(parentSlug);
        if (p == null)
            return false;
        return p.getParentSlug() == null;
    }

    // Validate is slug unique
    public boolean existsBySlugIgnoreCase(String slug) {
        return categoryRepository.findAll().stream()
                .anyMatch(c -> c.getSlug().equalsIgnoreCase(slug.trim()));
    }

    // Validate category name is unique under the same parent category
    public boolean existsByCategoryNameIgnoreCase(Category category) {
        return getChildCategoryName(category.getParentSlug()).stream()
                .anyMatch(existing -> existing.trim()
                        .equalsIgnoreCase(category.getCategoryName().trim()));
    }
}
