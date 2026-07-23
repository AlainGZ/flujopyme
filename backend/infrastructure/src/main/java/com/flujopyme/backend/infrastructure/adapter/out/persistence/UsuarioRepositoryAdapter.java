package com.flujopyme.backend.infrastructure.adapter.out.persistence;

import com.flujopyme.backend.domain.model.Correo;
import com.flujopyme.backend.domain.model.Usuario;
import com.flujopyme.backend.domain.port.out.UsuarioRepositoryPort;
import com.flujopyme.backend.infrastructure.adapter.out.persistence.mapper.UsuarioMapper;
import com.flujopyme.backend.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;

import java.util.Optional;

public class UsuarioRepositoryAdapter  implements UsuarioRepositoryPort {

	private final UsuarioJpaRepository jpaRepository;
	private final UsuarioMapper mapper;

	public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository, UsuarioMapper mapper){
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Usuario guardar(Usuario usuario) {
		var entidadGuardada = jpaRepository.save(mapper.aEntidad(usuario));
		return mapper.aDominio(entidadGuardada);
	}

	@Override
	public Optional<Usuario> buscarPorCorreo(Correo correo){
		return jpaRepository.findByCorreo(correo.valor()).map(mapper::aDominio);
	}

	@Override
	public boolean existePorCorreo(Correo correo){
		return jpaRepository.existsByCorreo(correo.valor());
	}

}
