package dev.aest.ark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public final class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NaturalId
    @Column(unique = true)
    @Email
    @NotBlank
    private String email;

    private String nickname;

    private String profilePicture;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LocalCredentials credentials;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<InventoryItem> ownedItems = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<PlannedItem> plannedItems = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;

        return email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
