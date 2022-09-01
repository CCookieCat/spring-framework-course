package com.ccat.springtest.dao;

import com.ccat.springtest.validation.FormValidationGroup;
import com.ccat.springtest.validation.PersistenceValidationGroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity //tell Hibernate to consider User.class as Bean
@Table(name = "Users") //which table User maps to ("tableName")
public class User implements Serializable {
    @NotNull(message="Username cannot be blank.", groups={PersistenceValidationGroup.class, FormValidationGroup.class})
    @Size(min=8, max=15, groups={PersistenceValidationGroup.class, FormValidationGroup.class})
    @Pattern(regexp = "^\\w{8,}$",
            groups={PersistenceValidationGroup.class, FormValidationGroup.class},
            message = "Username can only be lowercase and consist of numbers, letters and underscore characters.") //regex
    @Id
    @Column(name="username") //maps automatically if names corresponds
    private String username;

    @Email(groups={PersistenceValidationGroup.class, FormValidationGroup.class})
    private String email;

    @NotNull(groups={PersistenceValidationGroup.class, FormValidationGroup.class})
    @Pattern(regexp = "^\\S+$", message = "Password should not contain spaces.", groups={PersistenceValidationGroup.class, FormValidationGroup.class})
    @Size(min=5, max=15,
            groups={FormValidationGroup.class}, //remove PersistenceVGroup for size-checking Password (only WEB-Frontend check)
            message = "Password must be between 5 to 15 characters long.")
    private String password;
    private Boolean enabled;
    private String authority;

    @NotNull
    @Size(min=3, max=60)
    private String name;

    public User() {

    }

    public User(String username, String name,String password, String email, Boolean enabled, String authority) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
        this.email = email;
    }

    //GETTERs & SETTERs:
    public String getUsername() {
        return username;
    }
    public String getName() {
        return name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    // Hash/equals:

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (enabled != null ? !enabled.equals(user.enabled) : user.enabled != null) return false;
        if (authority != null ? !authority.equals(user.authority) : user.authority != null) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (authority != null ? authority.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", authority='" + authority + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
