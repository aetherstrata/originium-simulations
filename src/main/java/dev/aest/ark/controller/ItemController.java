package dev.aest.ark.controller;

import dev.aest.ark.entity.Item;
import dev.aest.ark.model.ItemType;
import dev.aest.ark.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ItemController
{
    public static final String NOT_FOUND = "items/notFound";

    private final ItemService itemService;

    @GetMapping("/items")
    public String getAllItemsPage(
            @RequestParam(value = "type", required = false) final String typeParam,
            Model model){
        ItemType itemType = ItemType.fromString(typeParam);
        if (itemType != null) {
            model.addAttribute("items", itemService.getAllItems(itemType));
        } else {
            model.addAttribute("items", itemService.getAllItems());
        }
        return "items/allItems";
    }

    @GetMapping("/item/{id}")
    public String getItemDetails(
            @PathVariable("id") final Long id,
            Model model){
        Item item = itemService.getFullItem(id);
        if (item == null) return NOT_FOUND;
        model.addAttribute("item", item);
        return "items/itemDetails";
    }
}
