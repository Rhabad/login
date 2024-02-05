package com.login.controller;

import com.login.model.entity.Register;
import com.login.model.payload.MensajeResponse;
import com.login.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class AuthController {

    @Autowired
    private IRegisterService registerService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Register register) {
        Register registerLogin = registerService.validarRegistro(register);

        if (registerLogin != null) {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Usted se ha Logueado")
                    .object(registerLogin)
                .build()
            , HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("Error de autenticacion")
                .object(null)
            .build()
        , HttpStatus.NOT_ACCEPTABLE);
    }

}
