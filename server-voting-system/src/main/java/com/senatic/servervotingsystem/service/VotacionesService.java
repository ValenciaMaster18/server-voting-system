package com.senatic.servervotingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.senatic.servervotingsystem.model.dto.VotacionDTO;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.enums.EstadoVotacion;

public interface VotacionesService {
    List<Votacion> getVotaciones();
    void addVotacion(Votacion votacion);
    void deleteById(Integer idVotacion);
    void deleteVotacion(Votacion votacion);
    Page<Votacion> getVotacionesPaginate(Pageable paging);
    Page<Votacion> getVotacionesPaginateByExample(Pageable paging, Example<Votacion> example);
    Optional<Votacion> getVotacionById(Integer idVotacion);
    void disableVotacionById(Integer idVotacion);
    void enableVotacionById(Integer idVotacion);
    List<Votacion> getVotacionesByEstado(EstadoVotacion estado);
    Boolean alreadyExist(VotacionDTO votacionDTO);
    Boolean alreadyExist(Integer idVotacion);
    Boolean isEnabled(Integer idVotacion);
    Boolean isDisabled(Integer idVotacion);
    Optional<Votacion> getCurrentVotacion();
    void setCurrentVotacion(Integer idVotacion);
    void setNotCurrentVotacion(Integer idVotacion);
    Boolean isAnyCurrentSelected();
    Boolean isThisCurrentVotacion(Integer idVotacion);
}
