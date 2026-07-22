package com.flujopyme.backend.domain.exception;

public class ContrasenaInvalidaException extends RuntimeException{
	public ContrasenaInvalidaException(String mensaje){
		super(mensaje);
	}
}
