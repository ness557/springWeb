package ness.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XmlRootElement
public class User implements Serializable {

    private int id;

    private String username;

    private String password;

    private UserInfo userInfo;

    @JsonBackReference
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean addRole(Role role){
        return roles.add(role);
    }

    public boolean removeRole(Role role){
        return roles.remove(role);
    }

    public String getRolesString(){

        StringBuilder buf = new StringBuilder();
        for (Role role: roles) {
            buf.append(role.getName());
            buf.append(", ");
        }
        String res = buf.toString();
        if(res.isEmpty())
            return "";
        return res.substring(0, res.length()-2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(userInfo, user.userInfo) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + username + '\'' +
                ", lastName='" + password + '\'' +
                ", userInfo=" + userInfo +
                ", roles=" + roles +
                '}';
    }
}
