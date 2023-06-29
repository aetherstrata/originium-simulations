package dev.aest.ark.dto;

import dev.aest.ark.model.Ingredient;
import dev.aest.ark.model.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientDto
{
    private Long item_id;
    private Integer quantity;
    private String name_en;
    private String name_cn;

    private IngredientDto(Ingredient ingredient){
        Item item = ingredient.getIngredient();
        this.item_id = item.getId();
        this.name_en = item.getNameEn();
        this.name_cn = item.getNameCn();
        this.quantity = ingredient.getQuantity();
    }

    public static IngredientDto of(Ingredient ingredient){
        if(ingredient == null) return null;
        return new IngredientDto(ingredient);
    }
}
