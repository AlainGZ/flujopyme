package com.flujopyme.backend.domain.port.out;

import com.flujopyme.backend.domain.model.Usuario;

import java.time.Instant;

public interface TokenGeneratorPort {

	TokenGenerado generar(Usuario usuario);

	record TokenGenerado(String valor, Instant expiracion){}
}
