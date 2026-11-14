package org.route.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @NotBlank(message = "Brand name must not be null")
    @Size(min = 2, max = 50, message = "Brand name must be between 2 and 50 characters")
    private String brandName;
    @NotBlank (message = "Slug must not be null")
    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
            message = "Slug must contain only lowercase letters, numbers and hyphens")
    private String slug;
    @Builder.Default
    private boolean active = true;
}
