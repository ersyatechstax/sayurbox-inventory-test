package com.main.persistence.repository;

import com.main.persistence.domain.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * created by ersya 30/03/2019
 */
@Repository
public interface TestRepository extends BaseRepository<Test> {

    Page<Test> findByCreatedDateBefore(Date date, Pageable pageable);
}
