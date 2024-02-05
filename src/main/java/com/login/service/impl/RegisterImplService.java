package com.login.service.impl;

import com.login.model.dao.RegisterDao;
import com.login.model.dto.RegisterDto;
import com.login.model.entity.Register;
import com.login.service.IRegisterService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterImplService implements IRegisterService {

    @Autowired
    private RegisterDao registerDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Register> findAll() {
        return (List<Register>) registerDao.findAll();
    }

    @Override
    @Transactional
    public Register save(RegisterDto registerDto) {
        Register register = Register.builder()
                    .id(registerDto.getId())
                    .nombre(registerDto.getNombre())
                    .apellido(registerDto.getApellido())
                    .email(registerDto.getEmail())
                    .password(registerDto.getPassword())
                .build();

        return registerDao.save(register);
    }

    @Override
    public Register validarRegistro(Register register) {
        String query = "from Register where email = :email and password = :password";
        List<Register> lista =entityManager.createQuery(query)
                .setParameter("email", register.getEmail())
                .setParameter("password", register.getPassword())
                .getResultList();

        if (lista.isEmpty()){
            return null;
        } else {
            return lista.get(0);
        }
    }
}
