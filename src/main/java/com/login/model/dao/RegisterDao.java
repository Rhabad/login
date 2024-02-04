package com.login.model.dao;

import com.login.model.entity.Register;
import org.springframework.data.repository.CrudRepository;

public interface RegisterDao extends CrudRepository<Register, Long> {
}
