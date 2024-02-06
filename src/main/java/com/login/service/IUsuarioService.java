package com.login.service;

import com.login.model.dto.UsuarioDto;
import com.login.model.entity.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> findAll();
    Usuario save(UsuarioDto usuarioDto);

    Usuario validarRegistro(Usuario usuario);
    void update(Usuario usuario);
}
