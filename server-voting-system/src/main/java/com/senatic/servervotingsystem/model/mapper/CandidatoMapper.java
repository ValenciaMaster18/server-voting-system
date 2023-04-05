package com.senatic.servervotingsystem.model.mapper;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.senatic.servervotingsystem.model.dto.CandidatoDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Imagen;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.enums.EstadoCandidato;
import com.senatic.servervotingsystem.service.AprendicesService;
import com.senatic.servervotingsystem.service.VotacionesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidatoMapper implements GenericMapper<Candidato, CandidatoDTO> {

    private final ImagenMapper imagenMapper;
    private final AprendicesService aprendicesService;
    private final VotacionesService votacionesService;

    @Override
    public Candidato dtoToPojo(CandidatoDTO dto) {
        Aprendiz aprendiz = aprendicesService.findById(dto.getDocumento()).get();
        Votacion votacion = votacionesService.getVotacionById(dto.getIdVotacion()).get();
        Imagen imagen = imagenMapper.dtoToPojo(dto.getImagen());
        imagen.setId(dto.getDocumento()); //Id is missing in imagenMapper
        Candidato candidato = Candidato.builder()
                .aprendiz(aprendiz)
                .imagen(imagen)
                .votacion(votacion)
                .propuestas(dto.getPropuestas())
                .estado(EstadoCandidato.HABILITADO)
                .build();
        if (dto.getId() != null) {
            candidato.setId(dto.getId());
        }
        return candidato;
    }

    @Override
    public CandidatoDTO pojoToDto(Candidato pojo) {
        MultipartFile multipartFile = imagenMapper.pojoToDto(pojo.getImagen());
        CandidatoDTO candidatoDTO = CandidatoDTO.builder()
                .documento(pojo.getAprendiz().getId())
                .imagen(multipartFile)
                .idVotacion(pojo.getVotacion().getId())
                .propuestas(pojo.getPropuestas())
                .build();
        return candidatoDTO;
    }

}
