package com.login.controller;

import com.login.model.dto.RegisterDto;
import com.login.model.entity.Register;
import com.login.model.payload.MensajeResponse;
import com.login.service.IRegisterService;
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
public class LoginController {

    @Autowired
    private IRegisterService registerService;

    // Mostrar los usuarios, es algo que no se implementara, pero sirve para mirar.
    @RequestMapping(value = "/show")
    public ResponseEntity<?> showAll() {
        List<Register> getList = registerService.findAll();

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
    public ResponseEntity<?> create(@RequestBody RegisterDto registerDto){
        Register registerSave = null;

        try {
            registerSave = registerService.save(registerDto);

            registerDto = RegisterDto.builder()
                        .id(registerSave.getId())
                        .nombre(registerSave.getNombre())
                        .apellido(registerSave.getApellido())
                        .email(registerSave.getEmail())
                        .password(registerSave.getPassword())
                    .build();

            return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Usuario Registado Con Exito")
                        .object(registerDto)
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

    /*
    * Este metodo enviara datos, confirmara en la BD
    * luego entrara si coincide el email y el password
    * con alguno de los registros en la BD
    * */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(){
        return null;
    }

}
