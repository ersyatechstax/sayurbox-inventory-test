package com.main.persistence.repository;

import com.main.persistence.domain.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * created by ersya 30/03/2019
 */
@NoRepositoryBean
public interface BaseRepository<T extends Base> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {
    Optional<T> findBySecureId(String secureId);
}
