package dev.aest.ark.controller;

import dev.aest.ark.entity.InventoryItem;
import dev.aest.ark.entity.Item;
import dev.aest.ark.entity.PlannedItem;
import dev.aest.ark.entity.User;
import dev.aest.ark.model.AddItemForm;
import dev.aest.ark.model.MissingItem;
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

import java.util.List;

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

    @GetMapping("/user/planner")
    public String getUserPlannerPage(Model model){
        User user = userService.getCurrentUser();
        if (user == null) return NOT_FOUND;
        List<MissingItem> missing = plannedItemService.getMissingItems(user);
        model.addAttribute("user", user);
        model.addAttribute("planner", plannedItemService.getUserPlannedItems(user));
        model.addAttribute("missing", missing);
        return "users/planner";
    }

    @GetMapping("/user/planner/add/{id}")
    public String getPlannerItemAddPage(
            @PathVariable("id") final Long id,
            Model model){
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        model.addAttribute("item", item);
        model.addAttribute("form", new AddItemForm());
        return "users/addToPlanner";
    }

    @PostMapping("/user/planner/add/{id}")
    public String addItemToPlanner(
            @PathVariable("id") final Long id,
            @Valid @ModelAttribute("form") AddItemForm addItemForm,
            BindingResult bindingResult,
            Model model){
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            return "users/addToPlanner";
        }
        User user = userService.getCurrentUser();
        PlannedItem storedItem = plannedItemService.getUserPlannedItem(user, item);
        if (storedItem == null) storedItem = new PlannedItem(user, item);
        plannedItemService.increaseItemBy(storedItem, addItemForm.getQuantity());
        return "redirect:/user/planner";
    }

    @PostMapping("/user/planner/addOne/{id}")
    public String addOneToPlanner(
            @PathVariable("id") final Long id){
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        User user = userService.getCurrentUser();
        PlannedItem storedItem = plannedItemService.getUserPlannedItem(user, item);
        if (storedItem == null) storedItem = new PlannedItem(user, item);
        plannedItemService.increaseItemBy(storedItem, 1);
        return "redirect:/user/planner";
    }

    @PostMapping("/user/planner/removeOne/{id}")
    public String removeOneToPlanner(
            @PathVariable("id") final Long id){
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        PlannedItem storedItem = plannedItemService.getUserPlannedItem(userService.getCurrentUser(), item);
        if (storedItem != null) plannedItemService.decreaseItemBy(storedItem, 1);
        return "redirect:/user/planner";
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
        model.addAttribute("item", item);
        model.addAttribute("form", new AddItemForm());
        return "users/addToInventory";
    }

    @PostMapping("/user/inventory/add/{id}")
    public String addItemToInventory(
            @PathVariable("id") final Long id,
            @Valid @ModelAttribute("form") AddItemForm addItemForm,
            BindingResult bindingResult,
            Model model){
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            return "users/addToInventory";
        }
        User user = userService.getCurrentUser();
        InventoryItem storedItem = inventoryItemService.getUserInventoryItem(user, item);
        if (storedItem == null) storedItem = new InventoryItem(user, item);
        inventoryItemService.increaseItemBy(storedItem, addItemForm.getQuantity());
        return "redirect:/user/inventory";
    }

    @PostMapping("/user/inventory/addOne/{id}")
    public String addOneToInventory(
            @PathVariable("id") final Long id){
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        User user = userService.getCurrentUser();
        InventoryItem storedItem = inventoryItemService.getUserInventoryItem(user, item);
        if (storedItem == null) storedItem = new InventoryItem(user, item);
        inventoryItemService.increaseItemBy(storedItem, 1);
        return "redirect:/user/inventory";
    }

    @PostMapping("/user/inventory/removeOne/{id}")
    public String removeOneToInventory(
            @PathVariable("id") final Long id){
        Item item = itemService.getItem(id);
        if (item == null) return ItemController.NOT_FOUND;
        InventoryItem storedItem = inventoryItemService.getUserInventoryItem(userService.getCurrentUser(), item);
        if (storedItem != null) inventoryItemService.decreaseItemBy(storedItem, 1);
        return "redirect:/user/inventory";
    }
}
