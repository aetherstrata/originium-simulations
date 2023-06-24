package dev.aest.ark.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;

import java.nio.file.Path;
import java.util.List;

@Data
@Entity
@Table(name = "items")
public final class Item
{
    @Id
    private Long id;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_cn")
    private String nameCn;

    @Column(length = 1023)
    private String description;

    @Column(length = 1023)
    private String usage;

    @Column(nullable = false)
    private Integer level;

    @ColumnDefault("0")
    private ItemType type;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Drop> sources;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    private List<Recipe> recipes;

    //Materialized view
    @Formula("(SELECT i.droppable FROM item_capabilities i WHERE i.item_id=id)")
    private Boolean canBeDropped;

    @Formula("(SELECT i.craftable FROM item_capabilities i WHERE i.item_id=id)")
    private Boolean canBeCrafted;

    public String getBackgroundImage(){
        return "images/bg/item-%d.png".formatted(level);
    }

    public String getForegroundImage(){
        return "images/item/%d.png".formatted(level);
    }
}
