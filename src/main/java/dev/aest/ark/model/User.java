package dev.aest.ark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private LocalCredentials credentials;

    @OneToMany(mappedBy = "user")
    private List<InventoryItem> ownedItems;

    @OneToMany(mappedBy = "user")
    private List<InventoryItem> plannedItems;
}
