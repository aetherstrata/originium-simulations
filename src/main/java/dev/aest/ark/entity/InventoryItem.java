package dev.aest.ark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "inventory_items", uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "item_id"})})
public final class InventoryItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Item item;

    @NotNull
    @Positive(message = "{number.positive}")
    private Integer quantity = 0;

    public InventoryItem(User user, Item item){
        this.user = user;
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryItem other)) return false;

        if (!user.equals(other.user)) return false;
        return item.equals(other.item);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + item.hashCode();
        return result;
    }
}
