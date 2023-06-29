package dev.aest.ark.service;

import dev.aest.ark.model.InventoryItem;
import dev.aest.ark.model.Item;
import dev.aest.ark.model.User;
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
        return this.inventoryItemRepository.findAllByUser(user);
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
    public void saveItem(InventoryItem inventoryItem) {
        this.inventoryItemRepository.save(inventoryItem);
    }

    public void deleteItem(InventoryItem inventoryItem) {
        this.inventoryItemRepository.delete(inventoryItem);
    }
}
