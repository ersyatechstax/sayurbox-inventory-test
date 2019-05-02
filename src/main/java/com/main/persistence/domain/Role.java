package com.main.persistence.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * created by ersya 30/03/2019
 */
@Data
@Entity
@Table(name = "ROLES")
public class Role extends Base{

    @Column(name = "NAME",length = 40)
    private String name;

}
