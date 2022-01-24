package br.com.api.spring.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.spring.dto.UsuarioDto;
import br.com.api.spring.entity.UsuarioEntity;
import br.com.api.spring.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private Logger log = Logger.getLogger(UsuarioService.class);

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public ResponseEntity<Page> searchAll(Pageable page) {
		try {
			Page<UsuarioEntity> usuario = usuarioRepository.findAll(page);
			usuario.map(item -> modelMapper.map(item, UsuarioDto.class));
			return ResponseEntity.ok(usuario);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();

		}

	}

	@Transactional
	public ResponseEntity<UsuarioDto> searchByCpf(String cpf) {
		try {
			log.info("Busca especifica por cpf");
			if (!usuarioRepository.existsByCpf(cpf)) {
				ResponseEntity.notFound().build();
			}
			Optional<UsuarioEntity> usuario = usuarioRepository.findByCpf(cpf);
			UsuarioEntity usuarioModel = usuario
					.orElseThrow(() -> new ServiceException("Erro na busca verifique os campos e tente novamente"));
			UsuarioDto usuarioDto = toDto(usuarioModel);
			return ResponseEntity.ok().body(usuarioDto);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Transactional
	public UsuarioDto save(UsuarioDto usuario) {
		UsuarioDto usuarioRetorno = null;
		try {
			UsuarioEntity usuarioEn = modelMapper.map(usuario, UsuarioEntity.class);
			UsuarioEntity usuarioEntity = usuarioRepository.save(usuarioEn);
			usuarioRetorno = modelMapper.map(usuarioEntity, UsuarioDto.class);
			log.info("Cliente cadastrado com Sucesso");
		} catch (Exception e) {
			throw new ServiceException("Erro ao cadastrar um novo usuario");
		}
		return usuarioRetorno;

	}

	@Transactional
	public UsuarioDto update(String cpf, UsuarioDto usuarioDto) {
		try {
			UsuarioEntity usuario = usuarioRepository.findByCpf(cpf).get();
			BeanUtils.copyProperties(usuarioDto, usuario, "usuario");
			UsuarioEntity toModel = toModel(usuarioDto);
			toModel.setCpf(usuario.getCpf());
			UsuarioEntity usuarioModel = usuarioRepository.save(toModel);
			return toDto(usuarioModel);
		} catch (BeansException e) {
			throw new ServiceException("Erro");
		}

	}

	@Transactional
	public ResponseEntity<UsuarioEntity> deletar(String cpf) {
		if (!usuarioRepository.existsByCpf(cpf)) {
			return ResponseEntity.notFound().build();
		}
		usuarioRepository.deleteByCpf(cpf);
		return ResponseEntity.noContent().build();
	}

	private UsuarioEntity toModel(UsuarioDto usuarioDto) {
		return modelMapper.map(usuarioDto, UsuarioEntity.class);
	}

	private UsuarioDto toDto(UsuarioEntity usuario) {
		return modelMapper.map(usuario, UsuarioDto.class);
	}

}
