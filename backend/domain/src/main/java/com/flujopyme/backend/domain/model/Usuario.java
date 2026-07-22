package com.flujopyme.backend.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class Usuario {

	private final UUID id;
	private final Correo correo;
	private final String contrasenaHash;
	private final Instant fechaRegistro;

	private Usuario(UUID id, Correo correo, String contrasenaHash, Instant fechaRegistro){
		this.id = Objects.requireNonNull(id, "El id del usuario es obligatorio");
		this.correo = Objects.requireNonNull(correo, "El correo del usuario es obligatorio");
		this.contrasenaHash = Objects.requireNonNull(contrasenaHash, "La contrasena del usuario es obligatoria");
		this.fechaRegistro = Objects.requireNonNull(fechaRegistro, "La fecha de registro es obligatoria");

	}

	public static Usuario crear(Correo correo, String contrasenaHash){
		return new Usuario(UUID.randomUUID(), correo, contrasenaHash, Instant.now());
	}

	public static Usuario reconstruir(UUID id, Correo correo, String contrasenaHash, Instant fechaRegistro){
		return new Usuario(id, correo, contrasenaHash, fechaRegistro);
	}

	public UUID id(){
		return id;
	}

	public Correo correo(){
		return correo;
	}

	public String contrasenaHash(){
		return contrasenaHash;
	}

	public Instant fechaRegistro(){
		return fechaRegistro;
	}

	@Override
	public boolean equals(Object o){
		if (this == o) return true;
		if (!(o instanceof Usuario usuario)) return false;
		return id.equals(usuario.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
