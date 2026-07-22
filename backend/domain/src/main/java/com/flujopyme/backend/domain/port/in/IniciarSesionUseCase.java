package com.flujopyme.backend.domain.port.in;

import com.flujopyme.backend.domain.port.out.TokenGeneratorPort;

public interface IniciarSesionUseCase {

	TokenGeneratorPort.TokenGenerado iniciarSesion(CredencialesComando comando);

	record CredencialesComando(String correo, String contrasenaPlano){}
}
