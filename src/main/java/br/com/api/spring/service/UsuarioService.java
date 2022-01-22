package br.com.api.spring.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.spring.dto.UsuarioDTO;
import br.com.api.spring.model.UsuarioModel;
import br.com.api.spring.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private Logger log = Logger.getLogger(UsuarioService.class);

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Page<UsuarioDTO> searchAll(Pageable pageable) {
		try {
			log.info("Retorna todos cadastrados");
			Page<UsuarioModel> usuario = usuarioRepository.findAll(pageable);
			return usuario.map(item -> modelMapper.map(item, UsuarioDTO.class));
		} catch (Exception e) {
			log.error("Erro ao efetuar ação , tente novamente!!! ");
		}
		return null;
	}

	public UsuarioDTO searchById(Long id) {
		UsuarioDTO usuarioRetorno = null;
		try {
			log.info("Busca específica por ID");
			Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
			UsuarioModel usuarioModel = usuario.orElseThrow(() -> new ServiceException("Erro"));
			UsuarioDTO usuarioDto = toDto(usuarioModel);
			return usuarioDto;
		} catch (ServiceException e) {
			log.error("Erro na busca verifique o id e tente novamente");
		}
		return usuarioRetorno;
	}

	@Transactional
	public UsuarioDTO save(UsuarioDTO usuario) {
		UsuarioDTO usuarioRetorno = null;
		try {
			log.info("Cadastrando um novo usuario");
			UsuarioModel usuarioModel = modelMapper.map(usuario, UsuarioModel.class);
			UsuarioModel usuarioM = usuarioRepository.save(usuarioModel);
			usuarioRetorno = modelMapper.map(usuarioM, UsuarioDTO.class);
			log.info("Usuario cadastrado com  sucesso");
		} catch (Exception e) {
			log.error("Erro ao cadastrar o usuário verifique os campos e tente novamente");
		}
		return usuarioRetorno;

	}

	@Transactional
	public UsuarioDTO update(Long id, UsuarioDTO usuarioDto) {
		try {
			log.info("Atualizando um usuario ");
			UsuarioModel usuario = usuarioRepository.findById();
			UsuarioDTO usuarioOld = toDto(usuario);
			BeanUtils.copyProperties(usuarioDto, usuarioOld, "usuario");
			UsuarioModel usuarioModel = toModel(usuarioDto);
			usuarioModel.setId(id);
			UsuarioModel usuarioUpdate = usuarioRepository.save(usuarioModel);
			log.info("Usuario Atualizado com sucesso");
			return toDto(usuarioUpdate);
		} catch (BeansException e) {
			log.error("Erro ao atualizar cliente verifique o id e tente novamente");
		}
		return usuarioDto;
	}

	@Transactional
	public ResponseEntity<UsuarioModel> deletar(Long id, UsuarioModel model) {
		if (!usuarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		usuarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	private UsuarioModel toModel(UsuarioDTO usuarioDto) {
		return modelMapper.map(usuarioDto, UsuarioModel.class);
	}

	private UsuarioDTO toDto(UsuarioModel usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

}
