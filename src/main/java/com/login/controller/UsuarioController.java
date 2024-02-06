package com.login.controller;

import com.login.model.dto.UsuarioDto;
import com.login.model.entity.Usuario;
import com.login.model.payload.MensajeResponse;
import com.login.service.IUsuarioService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    // Mostrar los usuarios, es algo que no se implementara, pero sirve para mirar.
    @RequestMapping(value = "/show")
    public ResponseEntity<?> showAll() {
        List<Usuario> getList = usuarioService.findAll();

        if (getList.isEmpty()) {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("No hay registro en la base de datos")
                    .object(null)
                .build()
            , HttpStatus.OK
            );
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("Registros encontrados")
                .object(getList)
            .build()
        , HttpStatus.OK
        );
    }

    /*
    * Crear un nuevo registro.
    * */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody UsuarioDto usuarioDto){
        Usuario usuario = null;

        try {
            usuario = usuarioService.save(usuarioDto);

            // trae el valor y luego lo encripta, el password.
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
            usuario.setPassword(hash);

            // actualizamos para que se guarde en la base de datos
            usuarioService.update(usuario);


            usuarioDto = UsuarioDto.builder()
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .email(usuario.getEmail())
                        .password(usuario.getPassword())
                    .build();


            return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Usuario Registado Con Exito")
                        .object(usuarioDto)
                    .build()
                , HttpStatus.CREATED);

        } catch (DataAccessException exData){
            return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje(exData.getMessage())
                        .object(null)
                    .build()
                , HttpStatus.CREATED);
        }
    }


}
