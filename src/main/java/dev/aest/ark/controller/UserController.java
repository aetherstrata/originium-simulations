package dev.aest.ark.controller;

import dev.aest.ark.model.InventoryItem;
import dev.aest.ark.model.Item;
import dev.aest.ark.model.User;
import dev.aest.ark.service.InventoryItemService;
import dev.aest.ark.service.ItemService;
import dev.aest.ark.service.PlannedItemService;
import dev.aest.ark.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController
{
    public static final String NOT_FOUND = "users/notFound";

    private final UserService userService;
    private final PlannedItemService plannedItemService;
    private final InventoryItemService inventoryItemService;
    private final ItemService itemService;

    @GetMapping("/user")
    public String getPlannerPanel(Model model){
        User user = userService.getCurrentUser();
        if (user == null) return NOT_FOUND;
        model.addAttribute("user", user);
        model.addAttribute("inventory_count", inventoryItemService.getInventoryItemCount(user));
        model.addAttribute("planned_count", plannedItemService.getPlannedItemCount(user));
        return "users/index";
    }

    @GetMapping("/user/inventory")
    public String getUserInventoryPage(Model model){
        User user = userService.getCurrentUser();
        if (user == null) return NOT_FOUND;
        model.addAttribute("user", user);
        model.addAttribute("inventory", inventoryItemService.getUserInventoryItems(user));
        return "users/inventory";
    }

    @GetMapping("/user/inventory/add/{id}")
    public String getInventoryItemAddPage(
            @PathVariable("id") final Long id,
            Model model){
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        InventoryItem newItem = new InventoryItem();
        newItem.setItem(item);
        model.addAttribute("item", item);
        model.addAttribute("inventory_item", newItem);
        return "users/addToInventory";
    }

    @PostMapping("/user/inventory/add/{id}")
    public String addItemToInventory(
            @PathVariable("id") final Long id,
            @Valid @ModelAttribute("inventory_item") InventoryItem newItem,
            BindingResult bindingResult,
            Model model){
        User user = userService.getCurrentUser();
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            return "users/addToInventory";
        }
        InventoryItem storedItem = inventoryItemService.getUserInventoryItem(user, item);
        if (storedItem == null) storedItem = new InventoryItem(user, item);
        inventoryItemService.increaseItemBy(storedItem, newItem.getQuantity());
        return "redirect:/user/inventory";
    }

    @PostMapping("/user/inventory/addOne/{id}")
    public String addOneToInventory(
            @PathVariable("id") final Long id){
        User user = userService.getCurrentUser();
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        InventoryItem storedItem = inventoryItemService.getUserInventoryItem(user, item);
        if (storedItem == null) storedItem = new InventoryItem(user, item);
        inventoryItemService.increaseItemBy(storedItem, 1);
        return "redirect:/user/inventory";
    }

    @PostMapping("/user/inventory/removeOne/{id}")
    public String removeOneToInventory(
            @PathVariable("id") final Long id){
        User user = userService.getCurrentUser();
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        InventoryItem storedItem = inventoryItemService.getUserInventoryItem(user, item);
        if (storedItem != null) inventoryItemService.decreaseItemBy(storedItem, 1);
        return "redirect:/user/inventory";
    }
}
