package org.route.controller;

import jakarta.validation.Valid;
import org.route.model.Brand;
import org.route.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public String getAllBrand(Model model){
        model.addAttribute("allBrands",brandService.getAllBrand());
        return "Brands/Brands";
    }

    @GetMapping("/brands/new")
    public String addNewBrands(Model newModel){
        newModel.addAttribute("newBrand", new Brand());
        return "Brands/NewBrands";
    }

    @PostMapping("/brands")
    public String addNewBrands(@ModelAttribute("newBrand") @Valid Brand newBrand,
                               BindingResult result){

        if(brandService.existsByBrandNameIgnoreCase(newBrand.getBrandName()))
            result.rejectValue("brandName","400","Brand name already exists");

        if(brandService.existByBrandSlugIgnoreCase(newBrand.getSlug()))
            result.rejectValue("slug","400","Slug already exists");

        if(result.hasErrors())
            return "Brands/NewBrands";

        brandService.addNewBrand(newBrand);
        return "redirect:/brands";
    }
}
