package dev.aest.ark.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddItemFormData
{
    @NotNull
    @Positive(message = "{number.positive}")
    private Integer quantity = 1;
}
