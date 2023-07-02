package dev.aest.ark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Ingredient other)) return false;

        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
