package com.flujopyme.backend.application.usecase;

import com.flujopyme.backend.domain.exception.ContrasenaInvalidaException;
import com.flujopyme.backend.domain.exception.CorreoYaRegistradoException;
import com.flujopyme.backend.domain.model.Correo;
import com.flujopyme.backend.domain.model.Usuario;
import com.flujopyme.backend.domain.port.in.RegistrarUsuarioUseCase;
import com.flujopyme.backend.domain.port.out.PasswordEncoderPort;
import com.flujopyme.backend.domain.port.out.UsuarioRepositoryPort;

public class RegistrarUsuarioUseCaseImpl implements RegistrarUsuarioUseCase {

	private static final int LONGITUD_MINIMA_CONTRASENA = 8;

	private final UsuarioRepositoryPort usuarioRepositoryPort;
	private final PasswordEncoderPort passwordEncoderPort;

	public RegistrarUsuarioUseCaseImpl(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort){
		this.usuarioRepositoryPort = usuarioRepositoryPort;
		this.passwordEncoderPort = passwordEncoderPort;
	}

	@Override
	public Usuario registrar(RegistrarUsuarioComando comando){
		Correo correo = Correo.de(comando.correo());

		if (usuarioRepositoryPort.existePorCorreo(correo)){
			throw new CorreoYaRegistradoException(correo.valor());
		}

		validarContrasena(comando.contrasenaPlano());

		String contrasenaHash = passwordEncoderPort.codificar(comando.contrasenaPlano());
		Usuario usuario = Usuario.crear(correo, contrasenaHash);
		return usuarioRepositoryPort.guardar(usuario);
	}

	private void validarContrasena(String contrasenaPlano){
		if (contrasenaPlano == null || contrasenaPlano.length() < LONGITUD_MINIMA_CONTRASENA){
			throw new ContrasenaInvalidaException("La contrasena debe tener al menos "+LONGITUD_MINIMA_CONTRASENA+" caracteres");
		}
	}


}
