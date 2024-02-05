package com.login.service.impl;

import com.login.model.dao.UsuarioDao;
import com.login.model.dto.UsuarioDto;
import com.login.model.entity.Usuario;
import com.login.service.IUsuarioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioImplService implements IUsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    @Transactional
    public Usuario save(UsuarioDto usuarioDto) {
        Usuario register = Usuario.builder()
                    .id(usuarioDto.getId())
                    .nombre(usuarioDto.getNombre())
                    .apellido(usuarioDto.getApellido())
                    .email(usuarioDto.getEmail())
                    .password(usuarioDto.getPassword())
                .build();

        return usuarioDao.save(register);
    }

    @Override
    public Usuario validarRegistro(Usuario usuario) {
        String query = "from Usuario where email = :email and password = :password";
        List<Usuario> lista =entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .setParameter("password", usuario.getPassword())
                .getResultList();

        if (lista.isEmpty()){
            return null;
        } else {
            return lista.get(0);
        }
    }
}
