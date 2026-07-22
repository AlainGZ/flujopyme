package com.flujopyme.backend.domain.port.in;

import com.flujopyme.backend.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {
	Usuario registrar(RegistrarUsuarioComando comando);

	record RegistrarUsuarioComando(String correo, String contrasenaPlano){}
}
