package org.route.repository.Category;


import org.route.model.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();

    Category findBySlug(String slug);

    void save(Category newCategory);
}
