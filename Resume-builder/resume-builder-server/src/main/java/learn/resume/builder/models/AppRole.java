package learn.resume.builder.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class AppRole {

    private int roleId;
    private String roleName;
    private List<AppUser> roleUsers;

    public AppRole(){

    }

    public AppRole(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public List<AppUser> getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(List<AppUser> roleUsers) {
        this.roleUsers = roleUsers;
    }

    public GrantedAuthority getAuthority(){
        return new SimpleGrantedAuthority(roleName);
    }
}
