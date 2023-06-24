package dev.aest.ark.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory_items", uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "item_id"})})
public final class InventoryItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Item item;

    private Integer quantity;
}
