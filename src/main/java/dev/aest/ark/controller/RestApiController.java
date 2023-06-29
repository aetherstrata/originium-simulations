package dev.aest.ark.controller;

import dev.aest.ark.dto.FullItemDto;
import dev.aest.ark.dto.ItemDto;
import dev.aest.ark.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RestApiController
{
    private final ItemService itemService;

    @GetMapping("items")
    public List<ItemDto> getAllItems(){
        List<ItemDto> itemList = itemService.getAllItems().stream().map(ItemDto::of).toList();
        if (itemList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no items in the database");
        return itemList;
    }

    @GetMapping("item/{id}")
    public FullItemDto getItemDetails(@PathVariable("id") final Long id){
        FullItemDto dto = FullItemDto.of(itemService.getFullItem(id));
        if (dto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This item does not exist");
        return dto;
    }
}
