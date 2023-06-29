package dev.aest.ark.controller;

import dev.aest.ark.dto.FullItemDto;
import dev.aest.ark.dto.ItemDto;
import dev.aest.ark.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RestApiController
{
    private final ItemService itemService;

    @GetMapping("items")
    public List<ItemDto> getAllItems(){
        return itemService.getAllItems().stream().map(ItemDto::of).toList();
    }

    @GetMapping("item/{id}")
    public FullItemDto getItemDetails(@PathVariable("id") final Long id){
        return FullItemDto.of(itemService.getFullItem(id));
    }
}
