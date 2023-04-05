package com.senatic.servervotingsystem.model.mapper;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.senatic.servervotingsystem.controller.exceptionHandler.exception.EntityNotFoundException;
import com.senatic.servervotingsystem.model.dto.CandidatoDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Imagen;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.enums.EstadoCandidato;
import com.senatic.servervotingsystem.service.AprendicesService;
import com.senatic.servervotingsystem.service.ImagenesService;
import com.senatic.servervotingsystem.service.VotacionesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidatoMapper implements GenericMapper<Candidato, CandidatoDTO> {

    private final AprendicesService aprendicesService;
    private final ImagenesService imagenesService;
    private final VotacionesService votacionesService;

    @Override
    public Candidato dtoToPojo(CandidatoDTO dto) {
        Aprendiz aprendiz = aprendicesService.findById(dto.getDocumento()).get();
        Votacion votacion = votacionesService.getVotacionById(dto.getIdVotacion()).get();
        Imagen imagen = Imagen.builder().build();
        if (dto.getImagen() == null) {
            Optional<Imagen> optionalImagen = imagenesService.getImagenById(dto.getDocumento());
            if (optionalImagen.isPresent()) {
                imagen = optionalImagen.get();
            } else {
                throw new EntityNotFoundException("IMAGE not found");
            }
        } else {
            imagen.setId(dto.getDocumento());
            imagen.setImage(dto.getImagen());
        }
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
        //MultipartFile multipartFile = imagenMapper.pojoToDto(pojo.getImagen());
        CandidatoDTO candidatoDTO = CandidatoDTO.builder()
                .documento(pojo.getAprendiz().getId())
                .imagen(pojo.getImagen().getImage())
                .idVotacion(pojo.getVotacion().getId())
                .propuestas(pojo.getPropuestas())
                .build();
        return candidatoDTO;
    }

}
