package dev.aest.ark.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ingredients", uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "ingredient_id"}))
public final class Ingredient
{
    @Id
    private Long id;

    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    private Item ingredient;

    @Column(nullable = false)
    private Integer quantity;
}
