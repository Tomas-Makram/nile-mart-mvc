package org.route.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @NotBlank(message = "SKU can't be empty.")
    private String sku;
    @NotBlank(message = "Name can't be null or empty.")
    private String name;
    @NotBlank(message = "Category can't be null or empty.")
    private String category;

    private String brandSlug;

    @NotNull(message = "Price can't be null or empty.")
    @Positive(message = "Price must be greater than zero.")
    private BigDecimal price;

    @NotNull(message = "Stock can't be null or empty.")
    @PositiveOrZero(message = "Stock must be greater than or equal zero.")
    private Integer inStock;
}
