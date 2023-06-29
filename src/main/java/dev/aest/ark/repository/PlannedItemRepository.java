package dev.aest.ark.repository;

import dev.aest.ark.model.PlannedItem;
import dev.aest.ark.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlannedItemRepository extends JpaRepository<PlannedItem, Long>
{
    List<PlannedItem> findAllByUser(User user);

    void deleteAllByUser(User user);

    @Query("SELECT SUM(i.quantity) FROM PlannedItem i WHERE i.user=:user")
    Long countItemsByUser(User user);
}
