package dev.aest.ark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Formula;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "drops", uniqueConstraints = @UniqueConstraint(columnNames = {"stage_id", "item_id"}))
public final class Drop
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

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;

        Drop other = (Drop) o;

        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
