package br.com.api.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.spring.dto.UsuarioDTO;
import br.com.api.spring.model.UsuarioModel;
import br.com.api.spring.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public Page<UsuarioDTO> searchAll(Pageable pageable) {
		try {
			return usuarioService.searchAll(pageable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchAll(pageable);
	}

	@GetMapping("{id}")
	public ResponseEntity<UsuarioDTO> searchById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(usuarioService.searchById(id));
		} catch (Exception ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioDTO usuario) {
		try {
			UsuarioDTO usuarioSave = usuarioService.save(usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSave);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody UsuarioDTO usuario) {
		try {
			UsuarioDTO usuarioUpdate = usuarioService.update(id, usuario);
			return ResponseEntity.ok(usuarioUpdate);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<UsuarioModel> deletar(@PathVariable Long id, UsuarioModel model) {
		try {
			usuarioService.deletar(id, model);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
