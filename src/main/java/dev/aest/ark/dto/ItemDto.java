package dev.aest.ark.dto;

import dev.aest.ark.entity.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto
{
    private Long id;
    private String name_en;
    private String name_cn;
    private String description;
    private String usage;
    private Integer level;
    private Integer type;
    private Boolean farmable;
    private Boolean craftable;
    private String image;

    protected ItemDto(Item item){
        this.id = item.getId();
        this.name_en = item.getNameEn();
        this.name_cn = item.getNameCn();
        this.description = item.getDescription();
        this.usage = item.getUsage();
        this.level = item.getLevel();
        this.type = item.getType().ordinal();
        this.farmable = item.getCanBeFarmed();
        this.craftable = item.getCanBeCrafted();
        this.image = item.getForegroundImage();
    }

    public static ItemDto of(Item item){
        if (item == null) return null;
        return new ItemDto(item);
    }
}
