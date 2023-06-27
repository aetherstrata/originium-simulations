package dev.aest.ark.service;

import dev.aest.ark.model.Item;
import dev.aest.ark.model.ItemType;
import dev.aest.ark.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService
{
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public Item getItem(Long id){
        return this.itemRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Item getFullItem(Long id){
        Item item = this.itemRepository.findById(id).orElse(null);
        if (item == null) return null;
        Hibernate.initialize(item.getRecipes());
        Hibernate.initialize(item.getRecipes());
        Hibernate.initialize(item.getSources());
        return item;
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItems(){
        return this.itemRepository.findAllByOrderById();
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItems(ItemType type){
        return this.itemRepository.findAllByTypeOrderById(type);
    }
}
