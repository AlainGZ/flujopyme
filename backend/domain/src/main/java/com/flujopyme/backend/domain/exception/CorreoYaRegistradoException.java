package com.flujopyme.backend.domain.exception;

public class CorreoYaRegistradoException extends  RuntimeException{
	public CorreoYaRegistradoException(String correo){
		super("Ya existe una cuenta registrada con el correo: "+correo);
	}
}
