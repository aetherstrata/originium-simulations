package dev.aest.ark.projection;

import dev.aest.ark.model.Item;

public record MissingItem(Item item, int quantity)
{
}
