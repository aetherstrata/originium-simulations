package dev.aest.ark.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Formula;

@Data
@Entity
@Table(name = "drops", uniqueConstraints = @UniqueConstraint(columnNames = {"stage_id", "item_id"}))
public class Drop
{
    @Id
    private Long id;

    @ManyToOne
    private GameStage stage;

    @ManyToOne
    private Item item;

    private Integer times;

    private Integer quantity;

    private Double standardDeviation;

    @Formula("(SELECT CAST(d.quantity AS FLOAT) / d.times FROM drops d WHERE d.id=id)")
    private Double dropRate;
}
