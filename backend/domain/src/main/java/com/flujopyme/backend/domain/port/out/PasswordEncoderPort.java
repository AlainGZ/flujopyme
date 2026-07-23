package com.flujopyme.backend.domain.port.out;

public interface PasswordEncoderPort {

	String codificar(String contrasenaPlano);

	boolean coincide(String contrasenaPlano, String contrasenaHash);
}
