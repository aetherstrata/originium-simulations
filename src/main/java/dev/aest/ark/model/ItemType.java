package dev.aest.ark.model;

public enum ItemType
{
    MISC,
    GOLD,
    BATTLE_RECORD,
    CARBON,
    CHIP,
    SKILL_SUMMARY,
    LEVEL_UP_MATERIAL;

    public static ItemType fromString(String typeParam) {
        if (typeParam == null) return null;
        return switch (typeParam) {
            case "misc" -> ItemType.MISC;
            case "gold" -> ItemType.GOLD;
            case "battle-records" -> ItemType.BATTLE_RECORD;
            case "carbon" -> ItemType.CARBON;
            case "chip" -> ItemType.CHIP;
            case "skill-summary" -> ItemType.SKILL_SUMMARY;
            case "material" -> ItemType.LEVEL_UP_MATERIAL;
            default -> null;
        };
    }
}

