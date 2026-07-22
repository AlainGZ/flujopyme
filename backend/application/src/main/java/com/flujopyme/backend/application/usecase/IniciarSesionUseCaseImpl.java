package com.flujopyme.backend.application.usecase;

import com.flujopyme.backend.domain.exception.CredencialesInvalidasException;
import com.flujopyme.backend.domain.model.Correo;
import com.flujopyme.backend.domain.model.Usuario;
import com.flujopyme.backend.domain.port.in.IniciarSesionUseCase;
import com.flujopyme.backend.domain.port.out.PasswordEncoderPort;
import com.flujopyme.backend.domain.port.out.TokenGeneratorPort;
import com.flujopyme.backend.domain.port.out.UsuarioRepositoryPort;

public class IniciarSesionUseCaseImpl implements IniciarSesionUseCase {

	private final UsuarioRepositoryPort usuarioRepositoryPort;
	private final PasswordEncoderPort passwordEncoderPort;
	private final TokenGeneratorPort tokenGeneratorPort;

	public IniciarSesionUseCaseImpl(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort, TokenGeneratorPort tokenGeneratorPort){
		this.usuarioRepositoryPort = usuarioRepositoryPort;
		this.passwordEncoderPort = passwordEncoderPort;
		this.tokenGeneratorPort = tokenGeneratorPort;
	}

	@Override
	public TokenGeneratorPort.TokenGenerado iniciarSesion(CredencialesComando comando){
		Correo correo;
		try {
			correo = Correo.de(comando.correo());
		} catch (IllegalArgumentException correoInvalido){
			throw new CredencialesInvalidasException();
		}

		Usuario usuario = usuarioRepositoryPort.buscarPorCorreo(correo).orElseThrow(CredencialesInvalidasException::new);

		if (!passwordEncoderPort.coincide(comando.contrasenaPlano(), usuario.contrasenaHash())){
			throw new CredencialesInvalidasException();
		}
		return tokenGeneratorPort.generar(usuario);
	}
}
