package com.main.persistence.repository;

import com.main.persistence.domain.Parameter;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends BaseRepository<Parameter> {
    Parameter findByCode(String code);
}
