package org.route.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @NotBlank(message = "Category name must not be null")
    @Size(min=2, max=50,message = "Category name must be between 2 and 50 characters")
    private String categoryName;
    @NotBlank(message = "Category slug must not be null")
    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
            message = "Slug must contain only lowercase letters, numbers and hyphens")
    private String slug;
    private String parentSlug;
    @NotNull(message = "Display order is required")
    @PositiveOrZero(message = "Display order must be zero or positive")
    private Integer displayOrder;

    @Builder.Default
    private boolean active = true;
    @Builder.Default
    private boolean isChild = false;
}
