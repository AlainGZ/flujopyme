package com.flujopyme.backend.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Correo {

	private static final Pattern FORMATO_VALIDO = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$");

	private final String valor;

	private Correo(String valor){
		this.valor = valor;
	}

	public static Correo de(String valor){
		if (valor == null || valor.isBlank()){
			throw new IllegalArgumentException("El correo no puede estar vacio");
		}
		String normalizado = valor.trim().toLowerCase();
		if (!FORMATO_VALIDO.matcher(normalizado).matches()){
			throw new IllegalArgumentException("El formato del correo no es valido: "+valor);
		}
		return new Correo(normalizado);
	}

	public String valor(){
		return valor;
	}

	@Override
	public boolean equals(Object o){
		if (this == o)return true;
		if (!(o instanceof Correo correo)) return false;
		return valor.equals(correo.valor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(valor);
	}

	@Override
	public String toString(){
		return valor;
	}
}
