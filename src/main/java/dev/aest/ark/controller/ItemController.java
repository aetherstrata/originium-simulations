package dev.aest.ark.controller;

import dev.aest.ark.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ItemController
{
    private final ItemRepository itemRepository;

    @GetMapping("/items")
    public String getAllItemsPage(Model model){
        model.addAttribute("items", itemRepository.findAll());
        return "items/allItems";
    }
}
