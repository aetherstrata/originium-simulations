package dev.aest.ark.dto;

import dev.aest.ark.model.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleItemDto
{
    private Long id;
    private String name_en;
    private String name_cn;

    protected SimpleItemDto(Item item){
        this.id = item.getId();
        this.name_en = item.getNameEn();
        this.name_cn = item.getNameCn();
    }

    public static SimpleItemDto of(Item item){
        if(item == null) return null;
        return new SimpleItemDto(item);
    }
}
