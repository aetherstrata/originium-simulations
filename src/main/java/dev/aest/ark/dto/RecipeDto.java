package dev.aest.ark.dto;

import dev.aest.ark.entity.Recipe;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDto
{
    private Integer quantity;
    private List<IngredientDto> ingredients;

    private RecipeDto(Recipe recipe){
        this.quantity = recipe.getQuantity();
        this.ingredients = recipe.getIngredients().stream().map(IngredientDto::of).toList();
    }

    public static RecipeDto of(Recipe recipe){
        if(recipe == null) return null;
        return new RecipeDto(recipe);
    }
}
