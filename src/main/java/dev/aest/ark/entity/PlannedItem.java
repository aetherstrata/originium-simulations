package dev.aest.ark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "planned_items", uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "item_id"})})
public final class PlannedItem
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

    public PlannedItem(User user, Item item){
        this.user = user;
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlannedItem other)) return false;

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
