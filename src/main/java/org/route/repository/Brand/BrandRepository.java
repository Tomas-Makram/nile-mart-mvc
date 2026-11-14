package org.route.repository.Brand;

import org.route.model.Brand;

import java.util.List;

public interface BrandRepository {

    List<Brand> findAll();

    Brand findBySlug(String slug);

    void save(Brand newBrand);
}
