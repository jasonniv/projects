package learn.resume.builder.security;

import learn.resume.builder.data.AppRoleRepo;
import learn.resume.builder.data.AppUserRepo;
import learn.resume.builder.models.AppRole;
import learn.resume.builder.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepo repository;
    private final AppRoleRepo roleRepo;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepo repository,
                          AppRoleRepo roleRepo,
                          PasswordEncoder encoder) {
        this.repository = repository;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return appUser;
    }

    public AppUser createJobSeeker(String username, String password) {
        validate(username);
        validatePassword(password);

        password = encoder.encode(password);

        AppRole role = roleRepo.findByName("Job Seeker");
        AppUser appUser = new AppUser(0, username, password, false, List.of(role));

        return repository.create(appUser);
    }

    private void validate(String username) {
        if (username == null || username.isBlank()) {
            throw new ValidationException("Username is required");
        }

        if (username.length() > 50) {
            throw new ValidationException("Username must be less than 50 characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters");
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        if (digits == 0 || letters == 0 || others == 0) {
            throw new ValidationException("Password must contain a digit, a letter, and a non-digit/non-letter");
        }
    }
}


