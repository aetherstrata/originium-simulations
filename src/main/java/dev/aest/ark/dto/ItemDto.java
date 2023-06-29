package dev.aest.ark.dto;

import dev.aest.ark.model.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItemDto extends SimpleItemDto
{
    private String description;
    private String usage;
    private Integer level;
    private Integer type;
    private Boolean farmable;
    private Boolean craftable;
    private String image;

    protected ItemDto(Item item){
        super(item);
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
