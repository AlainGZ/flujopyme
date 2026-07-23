package com.flujopyme.backend.domain.port.out;

import com.flujopyme.backend.domain.model.Correo;
import com.flujopyme.backend.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {

	Usuario guardar (Usuario usuario);

	Optional<Usuario> buscarPorCorreo(Correo correo);

	boolean existePorCorreo(Correo correo);

}
