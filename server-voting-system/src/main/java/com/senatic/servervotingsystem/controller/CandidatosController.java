package com.senatic.servervotingsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senatic.servervotingsystem.model.dto.CandidatoDTO;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.enums.EstadoCandidato;
import com.senatic.servervotingsystem.model.mapper.CandidatoMapper;
import com.senatic.servervotingsystem.service.CandidatosService;
import com.senatic.servervotingsystem.service.VotacionesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/candidatos")
@RequiredArgsConstructor
@Validated
public class CandidatosController {

    private final CandidatosService candidatosService;
    private final VotacionesService votacionesService;
    private final CandidatoMapper candidatoMapper;

    @GetMapping
    public ResponseEntity<Page<CandidatoDTO>> handleGetCandidatos(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "9") Integer size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Candidato> candidatos = candidatosService.getCandidatosPaginate(paging);
        if (candidatos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<CandidatoDTO> candidatosDTO = new PageImpl<CandidatoDTO>(
                candidatos.map(candidatoMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(candidatosDTO);
    }

    @GetMapping("/current-votacion")
    public ResponseEntity<List<CandidatoDTO>> handleGetCandidatoByCurrentVotacion() {

        Optional<Votacion> currentOptional = votacionesService.getCurrentVotacion();
        if (currentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Candidato> candidatos = candidatosService.getCandidatosByVotacionAndEstado(currentOptional.get().getId() , EstadoCandidato.HABILITADO);
        if (candidatos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<CandidatoDTO> candidatosDTO = candidatos.stream().map(candidatoMapper::pojoToDto).toList();
        
        return ResponseEntity.status(HttpStatus.OK).body(candidatosDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CandidatoDTO>> handleSearchCandidato(
            @RequestBody CandidatoDTO candidatoDTO,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "9") Integer size) {
        Example<Candidato> example = Example.of(candidatoMapper.dtoToPojo(candidatoDTO));
        Pageable paging = PageRequest.of(page, size);
        Page<Candidato> candidatos = candidatosService.getCandidatosPaginateByExample(paging, example);
        if (candidatos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<CandidatoDTO> candidatosDTO = new PageImpl<CandidatoDTO>(
                candidatos.map(candidatoMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(candidatosDTO);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> handleCreateCandidato(@RequestBody CandidatoDTO candidatoDTO) {
        Candidato candidato = candidatoMapper.dtoToPojo(candidatoDTO);
        if (candidatosService.alreadyExistOnVotacion(candidato)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        candidatosService.addCandidato(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<HttpStatus> handleUpdateCandidato(
            @RequestBody CandidatoDTO candidatoDTO) {
        if (!candidatosService.alreadyExist(candidatoDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Candidato candidato = candidatoMapper.dtoToPojo(candidatoDTO);
        candidatosService.addCandidato(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<HttpStatus> enableCandidatoById(@PathVariable("id") Integer idCandidato) {
        if (!candidatosService.alreadyExist(idCandidato)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if(candidatosService.alreadyEnabled(idCandidato)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } 
        candidatosService.enableCandidatoById(idCandidato);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<HttpStatus> disableCandidatoById(@PathVariable("id") Integer idCandidato) {
        if (!candidatosService.alreadyExist(idCandidato)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if(candidatosService.alreadyDisabled(idCandidato)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } 
        candidatosService.disableCandidatoById(idCandidato);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Convierte en null los string vacíos
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
