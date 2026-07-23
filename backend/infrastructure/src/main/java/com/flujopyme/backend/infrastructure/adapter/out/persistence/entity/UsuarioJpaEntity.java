package com.flujopyme.backend.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {

	@Id
	@Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "correo", nullable = false, unique = true, length = 255)
	private String correo;

	@Column(name = "contrasena_hash", nullable = false, length = 255)
	private String contrasenaHash;

	@Column(name = "fecha_registro", nullable = false, updatable = false)
	private Instant fechaRegistro;

	protected UsuarioJpaEntity(){

	}

	public UsuarioJpaEntity(UUID id, String correo, String contrasenaHash, Instant fechaRegistro) {
		this.id = id;
		this.correo = correo;
		this.contrasenaHash = contrasenaHash;
		this.fechaRegistro = fechaRegistro;
	}

	public UUID getId() {
		return id;
	}

	public String getCorreo() {
		return correo;
	}

	public String getContrasenaHash() {
		return contrasenaHash;
	}

	public Instant getFechaRegistro() {
		return fechaRegistro;
	}

}
