package dev.aest.ark.service;

import dev.aest.ark.model.*;
import dev.aest.ark.projection.MissingItem;
import dev.aest.ark.repository.PlannedItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannedItemService
{
    private final PlannedItemRepository plannedItemRepository;

    @Transactional(readOnly = true)
    public List<PlannedItem> getUserPlannedItems(User user) {
        return this.plannedItemRepository.findAllByUser(user);
    }

    @Transactional
    public void clearUserPlannedItems(User user) {
        this.plannedItemRepository.deleteAllByUser(user);
    }

    @Transactional(readOnly = true)
    public long getPlannedItemCount(User user) {
        Long count = this.plannedItemRepository.countItemsByUser(user);
        return count == null ? 0 : count;
    }

    @Transactional(readOnly = true)
    public PlannedItem getUserPlannedItem(User user, Item item) {
        return this.plannedItemRepository.findByUserAndItem(user, item);
    }

    @Transactional
    public void decreaseItemBy(PlannedItem plannedItem, int qty){
        if (plannedItem.getQuantity() <= qty){
            plannedItemRepository.delete(plannedItem);
        } else {
            plannedItem.setQuantity(plannedItem.getQuantity() - qty);
            plannedItemRepository.save(plannedItem);
        }
    }

    @Transactional
    public void increaseItemBy(PlannedItem plannedItem, int qty){
        plannedItem.setQuantity(plannedItem.getQuantity() + qty);
        plannedItemRepository.save(plannedItem);
    }

    @Transactional
    public List<MissingItem> getMissingItems(User user){
        return this.plannedItemRepository.getMissingItems(user);
    }
}
