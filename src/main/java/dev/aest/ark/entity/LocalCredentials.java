package dev.aest.ark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@Table(name = "credentials")
public final class LocalCredentials implements UserDetails
{
    public static final String DEFAULT_AUTHORITY = "REGISTERED";
    public static final String ADMIN_AUTHORITY = "ADMIN";

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @NaturalId
    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_.]+$", message = "{username.incorrect}")
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=(.*[A-Z])+)(?=(.*[a-z])+)(?=(.*[0-9])+)(?=(.*[!@#$%^&*()_+=.])+).{10,}$", message = "{password.weak}")
    @NotBlank
    private String password;

    private String authority;

    @ColumnDefault("true")
    private Boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(authority));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;

        LocalCredentials that = (LocalCredentials) o;

        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
