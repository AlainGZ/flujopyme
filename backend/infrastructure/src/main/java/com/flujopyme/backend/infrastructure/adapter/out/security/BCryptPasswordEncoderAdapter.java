package com.flujopyme.backend.infrastructure.adapter.out.security;

import com.flujopyme.backend.domain.port.out.PasswordEncoderPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

	private final PasswordEncoder passwordEncoder;

	public BCryptPasswordEncoderAdapter(PasswordEncoder passwordEncoder){
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String codificar(String contrasenaPlano){
		return passwordEncoder.encode(contrasenaPlano);
	}

	@Override
	public boolean coincide(String contrasenaPlano, String contrasenaHash){
		return passwordEncoder.matches(contrasenaPlano, contrasenaHash);
	}


}
