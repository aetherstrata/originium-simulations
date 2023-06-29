package dev.aest.ark.controller;

import dev.aest.ark.model.PageInfo;
import dev.aest.ark.model.User;
import dev.aest.ark.service.InventoryItemService;
import dev.aest.ark.service.PlannedItemService;
import dev.aest.ark.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AdminController
{
    private final UserService userService;
    private final InventoryItemService inventoryItemService;
    private final PlannedItemService plannedItemService;

    @GetMapping("/admin")
    public String getAdminPanel(Model model){
        return "admin/index";
    }

    @GetMapping("/admin/users")
    public String getUserListPage(
            Pageable pageable,
            Model model){
        Page<User> userPage = userService.getAllUsersPage(pageable);
        model.addAttribute("users", userPage.toList());
        model.addAttribute(PageInfo.ATTRIBUTE_NAME, new PageInfo(userPage));
        return "admin/usersPage";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserManagePage(
            @PathVariable("id") final Long id,
            Model model){
        User user = userService.getUser(id);
        if (user == null) return UserController.NOT_FOUND;
        model.addAttribute("user", user);
        return "admin/manageUser";
    }

    @PostMapping("/admin/user/{id}/clearInventoryItems")
    public String deleteUserInventoryItems(
            @PathVariable("id") final Long id){
        User user = userService.getUser(id);
        if (user == null) return UserController.NOT_FOUND;
        inventoryItemService.clearUserInventory(user);
        return "redirect:/admin/user/%d".formatted(user.getId());
    }

    @PostMapping("/admin/user/{id}/clearPlannedItems")
    public String deleteUserPlannedItems(
            @PathVariable("id") final Long id){
        User user = userService.getUser(id);
        if (user == null) return UserController.NOT_FOUND;
        plannedItemService.clearUserPlanner(user);
        return "redirect:/admin/user/%d".formatted(user.getId());
    }
}
