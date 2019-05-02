package com.main.persistence.domain;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * created by ersya 30/03/2019
 */
@Data
@Entity
@Table(name = "TEST")
public class Test extends Base {

    @Column(name = "NAME")
    private String name;

}
