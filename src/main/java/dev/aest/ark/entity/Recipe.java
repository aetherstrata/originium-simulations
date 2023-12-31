package dev.aest.ark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "recipes")
public final class Recipe
{
    @Id
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private Item result;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Recipe other)) return false;

        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
