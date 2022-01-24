package br.com.api.spring.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.spring.dto.UsuarioDto;
import br.com.api.spring.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<Page> searchAll(Pageable pageable) {
		try {
			return usuarioService.searchAll(pageable);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return searchAll(pageable);
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<ResponseEntity<UsuarioDto>> searchByCpf(@PathVariable String cpf) {
		try {
			return ResponseEntity.ok(usuarioService.searchByCpf(cpf));
		} catch (Exception ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<UsuarioDto> save(@Valid @RequestBody UsuarioDto usuarioDto) {
		try {
			UsuarioDto usuario = usuarioService.save(usuarioDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
		} catch (Exception ex) {
			ex.getMessage();
			return ResponseEntity.noContent().build();
		}

	}

	@PutMapping("/{cpf}")
	@Transactional
	public ResponseEntity<UsuarioDto> update(@Valid @PathVariable String cpf, @RequestBody UsuarioDto usuarioDto) {
		try {
			UsuarioDto usuarioDtoUpdate = usuarioService.update(cpf, usuarioDto);
			return ResponseEntity.ok(usuarioDtoUpdate);
		} catch (Exception ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{cpf}")
	@Transactional
	public ResponseEntity<UsuarioDto> delete(@PathVariable String cpf) {
		try {
			usuarioService.deletar(cpf);
			return ResponseEntity.noContent().build();
		} catch (Exception ex) {
			return ResponseEntity.notFound().build();
		}
	}
}