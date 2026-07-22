package com.flujopyme.backend.domain.exception;

public class CredencialesInvalidasException extends RuntimeException{
	public CredencialesInvalidasException(){
		super("El correo o la contrasena son incorrectos");
	}
}
