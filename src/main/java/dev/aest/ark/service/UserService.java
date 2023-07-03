package dev.aest.ark.service;

import dev.aest.ark.auth.OAuth2Credentials;
import dev.aest.ark.entity.LocalCredentials;
import dev.aest.ark.entity.User;
import dev.aest.ark.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final ProfilePictureService profilePictureService;
    private final UserRepository userRepository;

    /**
     * Retrieve a {@link User} from the database based on its ID.
     * @param id the id of the {@link User} to retrieve from the database
     * @return the retrieved {@link User}, or null if no {@link User} with the passed ID could be found in the database
     */
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    /**
     * Retrieve a {@link User} from the database based on its ID and its related associations.
     * @param id the id of the {@link User} to retrieve from the database with its associations initialized
     * @return the retrieved {@link User}, or null if no {@link User} with the passed ID could be found in the database
     */
    @Transactional(readOnly = true)
    public User getFullUser(Long id){
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) return null;
        Hibernate.initialize(user.getOwnedItems());
        Hibernate.initialize(user.getPlannedItems());
        return user;
    }

    /**
     * Retrieve the current {@link User} from the database.
     * @return the retrieved {@link User}, or null if no {@link User} is logged in
     */
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LocalCredentials local) return local.getUser();
        else if (principal instanceof OAuth2Credentials oauth) return oauth.getUser();
        else return null;
    }

    /**
     * Save a {@link User} in the database.
     * @param user the {@link User} to save into the database
     * @throws DataIntegrityViolationException if a {@link User} with the same username
     *                              as the passed User already exists in the database
     */
    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public void deleteUser(User user) {
        this.profilePictureService.delete(user.getProfilePicture());
        this.userRepository.delete(user);
    }

    /**
     * Retrieve all {@link User Users} from the database.
     * @return a List with all the retrieved {@link User Users}
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsersPage(Pageable pageable){
        return this.userRepository.findAll(pageable);
    }
}
