package dev.aest.ark.service;

import dev.aest.ark.model.Item;
import dev.aest.ark.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService
{
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public Item getItem(Long id){
        return this.itemRepository.findById(id).orElse(null);
    }
}
