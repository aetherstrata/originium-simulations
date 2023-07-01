package dev.aest.ark.service;

import dev.aest.ark.entity.InventoryItem;
import dev.aest.ark.entity.Item;
import dev.aest.ark.entity.User;
import dev.aest.ark.repository.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryItemService
{
    private final InventoryItemRepository inventoryItemRepository;

    @Transactional(readOnly = true)
    public long getInventoryItemCount(User user){
        Long count = this.inventoryItemRepository.countItemsByUser(user);
        return count == null ? 0 : count;
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getUserInventoryItems(User user){
        return this.inventoryItemRepository.findAllByUserOrderByItemId(user);
    }

    @Transactional(readOnly = true)
    public InventoryItem getUserInventoryItem(User user, Item item){
        return this.inventoryItemRepository.findByUserAndItem(user, item).orElse(null);
    }

    @Transactional
    public void clearUserInventory(User user) {
        this.inventoryItemRepository.deleteAllByUser(user);
    }

    @Transactional
    public void decreaseItemBy(InventoryItem inventoryItem, int qty){
        if (inventoryItem.getQuantity() <= qty){
            inventoryItemRepository.delete(inventoryItem);
        } else {
            inventoryItem.setQuantity(inventoryItem.getQuantity() - qty);
            inventoryItemRepository.save(inventoryItem);
        }
    }

    @Transactional
    public void increaseItemBy(InventoryItem inventoryItem, int qty){
        inventoryItem.setQuantity(inventoryItem.getQuantity() + qty);
        inventoryItemRepository.save(inventoryItem);
    }
}
