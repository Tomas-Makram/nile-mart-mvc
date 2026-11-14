package org.route.controller;

import jakarta.validation.Valid;
import org.route.model.Category;
import org.route.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model){
        model.addAttribute("allCategories", categoryService.getAllCategory());
        model.addAttribute("categoryNames", categoryService.getCategoryNamesBySlug());
        return "/Categories/Categories";
    }

    @GetMapping("/categories/new")
    public String addNewCategory(Model model){
        model.addAttribute("newCategory",new Category());
        model.addAttribute("parents", categoryService.getTopLevelCategories());
        return "/Categories/NewCategory";
    }

    @PostMapping("/categories")
    public String addNewCategory(@ModelAttribute("newCategory") @Valid Category newCategory,
                                 BindingResult result,Model model){

        if (!result.hasFieldErrors("slug")
                && categoryService.existsBySlugIgnoreCase(newCategory.getSlug())) {
            result.rejectValue("slug","400","Slug already exists");
        }


        if (!result.hasFieldErrors("categoryName")
                && categoryService.existsByCategoryNameIgnoreCase(newCategory)) {
            String ps = newCategory.getParentSlug();
            boolean topLevel = ps == null || ps.isBlank();
            result.rejectValue("categoryName", "category.name.duplicate",
                    topLevel ? "A top-level category with this name already exists"
                            : "A category with this name already exists under "
                              + categoryService.getCategoryNameBySlug(ps));
        }

        if(!categoryService.validateParent(newCategory.getParentSlug()))
                result.rejectValue("parentSlug", "400", "Parent category does not exist or must be a top-level");

        if(result.hasErrors()) {
            model.addAttribute("parents", categoryService.getTopLevelCategories());
            return "/Categories/NewCategory";
        }
        categoryService.addNewCategory(newCategory);
        return "redirect:/categories";
    }
}
