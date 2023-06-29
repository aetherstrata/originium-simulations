package dev.aest.ark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public final class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    @Email
    @NotBlank
    private String email;

    private String nickname;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private LocalCredentials credentials;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<InventoryItem> ownedItems = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<PlannedItem> plannedItems = new ArrayList<>();
}
