package dev.aest.ark.repository;

import dev.aest.ark.model.Item;
import dev.aest.ark.model.PlannedItem;
import dev.aest.ark.projection.MissingItem;
import dev.aest.ark.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlannedItemRepository extends JpaRepository<PlannedItem, Long>
{
    PlannedItem findByUserAndItem(User user, Item item);

    List<PlannedItem> findAllByUserOrderByItemId(User user);

    void deleteAllByUser(User user);

    @Query("SELECT SUM(i.quantity) FROM PlannedItem i WHERE i.user=:user")
    Long countItemsByUser(User user);

    @Query("""
            SELECT NEW dev.aest.ark.projection.MissingItem(i, plan.quantity - COALESCE(inv.quantity,0))
            FROM PlannedItem plan
            INNER JOIN Item i ON plan.item=i
            LEFT JOIN InventoryItem inv ON plan.item=inv.item AND plan.user=inv.user
            WHERE plan.user=:user AND (inv IS NULL OR plan.quantity > inv.quantity)
            ORDER BY i.id
            """)
    List<MissingItem> getMissingItems(User user);
}
