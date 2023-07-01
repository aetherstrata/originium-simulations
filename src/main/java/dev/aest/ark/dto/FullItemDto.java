package dev.aest.ark.dto;

import dev.aest.ark.entity.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FullItemDto extends ItemDto
{
    private List<RecipeDto> recipes;
    private List<DropDto> sources;

    private FullItemDto(Item item){
        super(item);
        this.recipes = item.getRecipes().stream().map(RecipeDto::of).toList();
        this.sources = item.getSources().stream().map(DropDto::of).toList();
    }

    public static FullItemDto of(Item item){
        if (item == null) return null;
        return new FullItemDto(item);
    }
}
