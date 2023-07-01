package dev.aest.ark.repository;

import dev.aest.ark.entity.LocalCredentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<LocalCredentials, Long>
{
    Optional<LocalCredentials> findByUsername(String username);
}
