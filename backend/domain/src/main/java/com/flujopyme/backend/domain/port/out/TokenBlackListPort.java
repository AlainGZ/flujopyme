package com.flujopyme.backend.domain.port.out;

public interface TokenBlackListPort {

	void invalidar(String token);

	boolean estaInvalidado(String token);
}
