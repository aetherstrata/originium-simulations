package dev.aest.ark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "planned_items", uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "item_id"})})
public final class PlannedItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Item item;

    @NotNull
    @Positive
    private Integer quantity = 0;

    public PlannedItem(User user, Item item){
        this.user = user;
        this.item = item;
    }
}
