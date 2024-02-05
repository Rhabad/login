package com.login.service;

import com.login.model.dto.RegisterDto;
import com.login.model.entity.Register;

import java.util.List;

public interface IRegisterService {
    List<Register> findAll();
    Register save(RegisterDto registerDto);

    Register validarRegistro(Register register);
}
