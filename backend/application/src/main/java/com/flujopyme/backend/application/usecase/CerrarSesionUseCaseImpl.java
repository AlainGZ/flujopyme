package com.flujopyme.backend.application.usecase;

import com.flujopyme.backend.domain.exception.TokenInvalidoException;
import com.flujopyme.backend.domain.port.in.CerrarSesionUseCase;
import com.flujopyme.backend.domain.port.out.TokenBlackListPort;

public class CerrarSesionUseCaseImpl implements CerrarSesionUseCase {

	private final TokenBlackListPort tokenBlackListPort;

	public CerrarSesionUseCaseImpl(TokenBlackListPort tokenBlackListPort){
		this.tokenBlackListPort = tokenBlackListPort;
	}

	@Override
	public void cerrarSesion(String token){
		if (token == null || token.isBlank()){
			throw new TokenInvalidoException("El token no puede estar vacio o nulo");
		}
		tokenBlackListPort.invalidar(token);
	}

}
