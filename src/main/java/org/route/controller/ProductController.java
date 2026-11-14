package org.route.controller;

import jakarta.validation.Valid;
import org.route.model.Product;
import org.route.service.BrandService;
import org.route.service.CategoryService;
import org.route.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProductController {

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, BrandService brandService, CategoryService categoryService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("availableProducts", productService.getAvailableProducts());
        model.addAttribute("categoryNames", categoryService.getCategoryNamesBySlug());
        model.addAttribute("brandNames", brandService.getBrandNamesBySlug());
        return "Product/Products";
    }

    @GetMapping("/products/new")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getSubCategories());
        model.addAttribute("brands", brandService.getActiveBrand());
        return "Product/NewProduct";
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult result, Model model) {
        if (productService.isProductExist(product.getSku()))
            result.rejectValue("sku", "400", "Sku is already exist");

        if(!productService.isCategoryValid(product.getCategory()))
                result.rejectValue("category", "400", "Category must be a sub-category");

        if(!productService.isBrandValid(product.getBrandSlug()))
                result.rejectValue("brandSlug", "400", "Brand does not exist or is inactive");

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getSubCategories());
            model.addAttribute("brands", brandService.getActiveBrand());
            return "Product/NewProduct";
        }
        productService.addNewProduct(product);
        return "redirect:/products";
    }
}
