package com.senatic.servervotingsystem.model.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senatic.servervotingsystem.model.dto.AprendizDTO;
import com.senatic.servervotingsystem.model.dto.UsuarioDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Usuario;
import com.senatic.servervotingsystem.model.entity.enums.EstadoAprendiz;
import com.senatic.servervotingsystem.model.entity.enums.TipoDocumento;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AprendizMapper implements GenericMapper<Aprendiz, AprendizDTO> {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Aprendiz dtoToPojo(AprendizDTO dto) {
        Usuario usuario = usuarioMapper.dtoToPojo(UsuarioDTO.builder()
                .username(dto.getNumeroDocumento())
                .password(passwordEncoder.encode(dto.getNumeroDocumento()))
                .authority("ROLE_APRENDIZ")
                .build());
        Aprendiz aprendiz = Aprendiz.builder()
                .id(dto.getNumeroDocumento())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .tipoDocumento(TipoDocumento.checkAndAssign(dto.getTipoDocumento()))
                .usuario(usuario)
                .celular(dto.getCelular())
                .correoElectronico(dto.getCorreoElectronico())
                .ficha(dto.getFicha())
                .programa(dto.getPrograma())
                .estado(EstadoAprendiz.checkAndAssign(dto.getEstado()))
                .build();
        return aprendiz;
    }

    @Override
    public AprendizDTO pojoToDto(Aprendiz pojo) {
        AprendizDTO aprendizDTO = AprendizDTO.builder()
                .nombre(pojo.getNombre())
                .apellido(pojo.getApellido())
                .numeroDocumento(pojo.getId())
                .tipoDocumento(pojo.getTipoDocumento().toString())
                .celular(pojo.getCelular())
                .correoElectronico(pojo.getCorreoElectronico())
                .ficha(pojo.getFicha())
                .programa(pojo.getPrograma())
                .estado(pojo.getEstado().getPlainText())
                .build();
        return aprendizDTO;
    }

}
