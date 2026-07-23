package com.flujopyme.backend.infrastructure.adapter.out.persistence.repository;

import com.flujopyme.backend.infrastructure.adapter.out.persistence.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, UUID> {

	Optional<UsuarioJpaEntity> findByCorreo(String correo);

	boolean existsByCorreo(String correo);
}
