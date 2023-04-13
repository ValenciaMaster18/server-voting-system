package com.senatic.servervotingsystem.service;

import java.util.List;

import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.Voto;

public interface VotosService {
    Boolean hasAlreadyVote(String idAprendiz, Integer idVotacion);
    Boolean hasAlreadyVote(Aprendiz aprendiz, Votacion votacion);
    List<Voto> getByVotacion(Votacion votacion);
    long countByVotacion(Votacion votacion);
    List<Voto> getByVotacionAndCandidato(Votacion votacion, Candidato candidato);
    void registerVote(Voto voto);
    void registerVote(Integer idCandidato, String idAprendiz);
    Boolean hasAlreadyVote(String authority);
}
