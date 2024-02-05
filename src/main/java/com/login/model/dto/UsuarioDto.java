package com.login.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UsuarioDto implements Serializable {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}
