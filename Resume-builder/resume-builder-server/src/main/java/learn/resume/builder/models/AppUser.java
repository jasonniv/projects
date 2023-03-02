package learn.resume.builder.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUser extends User {
    private static final String AUTHORITY_PREFIX = "ROLE_";

    private int userId;
    private List<AppRole> userRoles;

    public AppUser(int userId, String username, String password,
                   boolean disabled, List<AppRole> roles) {
        super(username, password, !disabled,
                true, true, true,
                convertRolesToAuthorities(roles));
        this.userId = userId;
        this.userRoles = roles;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<AppRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<AppRole> userRoles) {
        this.userRoles = userRoles;
    }

    public static List<GrantedAuthority> convertRolesToAuthorities(List<AppRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (AppRole role : roles) {
            Assert.isTrue(!role.getRoleName().startsWith(AUTHORITY_PREFIX),
                    () ->
                            String.
                                    format("%s cannot start with %s (it is automatically added)",
                                            role, AUTHORITY_PREFIX));
            authorities.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + role.getRoleName()));
        }
        return authorities;
    }

    public static List<String> convertAuthoritiesToRoles(Collection<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(a -> a.getAuthority().substring(AUTHORITY_PREFIX.length()))
                .collect(Collectors.toList());
    }
}
