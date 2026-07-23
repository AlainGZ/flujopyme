package com.flujopyme.backend.infrastructure.adapter.out.persistence.mapper;

import com.flujopyme.backend.domain.model.Correo;
import com.flujopyme.backend.domain.model.Usuario;
import com.flujopyme.backend.infrastructure.adapter.out.persistence.entity.UsuarioJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

	public UsuarioJpaEntity aEntidad(Usuario usuario){
		return new UsuarioJpaEntity(
				usuario.id(),
				usuario.correo().valor(),
				usuario.contrasenaHash(),
				usuario.fechaRegistro()
		);
	}

	public Usuario aDominio(UsuarioJpaEntity entidad){
		return Usuario.reconstruir(
				entidad.getId(),
				Correo.de(entidad.getCorreo()),
				entidad.getContrasenaHash(),
				entidad.getFechaRegistro()
		);
	}
}
