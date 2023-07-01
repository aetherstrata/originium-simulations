package dev.aest.ark.model;

import dev.aest.ark.entity.Item;

public record MissingItem(Item item, int quantity)
{
}
