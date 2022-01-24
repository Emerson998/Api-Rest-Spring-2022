package br.com.api.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.spring.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	boolean existsByCpf(String cpf);

	Optional<UsuarioEntity> findByCpf(String cpf);

	void deleteByCpf(String cpf);



}
