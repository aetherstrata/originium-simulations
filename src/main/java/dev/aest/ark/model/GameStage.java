package dev.aest.ark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
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
}
