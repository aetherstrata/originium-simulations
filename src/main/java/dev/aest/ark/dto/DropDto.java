package dev.aest.ark.dto;

import dev.aest.ark.model.Drop;
import dev.aest.ark.model.GameStage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DropDto
{
    private String stage_id;
    private String stage_name;
    private Double drop_rate;
    private Double std_dev;

    private DropDto(Drop drop){
        GameStage stage = drop.getStage();
        this.stage_id = stage.getId();
        this.stage_name = stage.getName();
        this.drop_rate = drop.getDropRate();
        this.std_dev = drop.getStandardDeviation();
    }

    public static DropDto of(Drop drop){
        if (drop == null) return null;
        return new DropDto(drop);
    }
}
