package com.main.persistence.domain;

import com.main.enums.AccountGroup;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

/**
 * created by ersya 30/03/2019
 */
@Data
@Entity
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User extends Base {

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "USERNAME")
    private String username;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_GROUP",length = 8)
    private AccountGroup accountGroup;


    @Column(name = "ACTIVE")
    private Boolean active;

    @PrePersist
    public void prePersist(){
        super.prePersist();
        this.active = true;
    }
}
