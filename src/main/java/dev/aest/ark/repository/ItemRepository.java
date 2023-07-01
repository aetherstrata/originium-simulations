package dev.aest.ark.repository;

import dev.aest.ark.entity.Item;
import dev.aest.ark.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>
{
    List<Item> findAllByTypeOrderById(ItemType type);

    List<Item> findAllByOrderById();
}
