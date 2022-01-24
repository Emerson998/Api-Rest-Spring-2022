package br.com.api.spring.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UsuarioDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String cpf;
	private Long senha;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data = LocalDate.now();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getSenha() {
		return senha;
	}

	public void setSenha(Long senha) {
		this.senha = senha;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "UsuarioDto [email=" + email + ", cpf=" + cpf + ", senha=" + senha + ", data=" + data + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, data, email, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDto other = (UsuarioDto) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(data, other.data) && Objects.equals(email, other.email)
				&& Objects.equals(senha, other.senha);
	}

}