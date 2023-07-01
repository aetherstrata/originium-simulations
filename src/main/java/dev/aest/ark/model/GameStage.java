package dev.aest.ark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="game_stages")
public final class GameStage
{
    @Id
    private String id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL)
    private List<Drop> drops;

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;

        GameStage other = (GameStage) o;

        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
