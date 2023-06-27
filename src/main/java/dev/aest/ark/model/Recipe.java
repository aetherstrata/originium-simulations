package dev.aest.ark.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
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
}
