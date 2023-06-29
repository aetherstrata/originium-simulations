package dev.aest.ark.repository;

import dev.aest.ark.model.InventoryItem;
import dev.aest.ark.model.Item;
import dev.aest.ark.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends CrudRepository<InventoryItem, Long>
{
    List<InventoryItem> findAllByUserOrderByItemId(User user);

    void deleteAllByUser(User user);

    Optional<InventoryItem> findByUserAndItem(User user, Item item);

    @Query("SELECT SUM(i.quantity) FROM InventoryItem i WHERE i.user=:user")
    Long countItemsByUser(User user);
}
