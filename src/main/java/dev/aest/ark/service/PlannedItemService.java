package dev.aest.ark.service;

import dev.aest.ark.model.PlannedItem;
import dev.aest.ark.model.User;
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
}
