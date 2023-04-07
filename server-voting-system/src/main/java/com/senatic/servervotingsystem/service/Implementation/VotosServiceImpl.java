package com.senatic.servervotingsystem.service.Implementation;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.Voto;
import com.senatic.servervotingsystem.repository.VotosRepository;
import com.senatic.servervotingsystem.service.AprendicesService;
import com.senatic.servervotingsystem.service.CandidatosService;
import com.senatic.servervotingsystem.service.VotacionesService;
import com.senatic.servervotingsystem.service.VotosService;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class VotosServiceImpl implements VotosService {

    private final VotosRepository votosRepository;
    private final AprendicesService aprendicesService;
    private final VotacionesService votacionesService;
    private final CandidatosService candidatosService;

    @Override
    public Boolean hasAlreadyVote(String idAprendiz, Integer idVotacion) {
        Aprendiz aprendiz = aprendicesService.getAprendizById(idAprendiz).get();
        Votacion votacion = votacionesService.getVotacionById(idVotacion).get();
        return votosRepository.findByAprendizAndVotacion(aprendiz, votacion).isPresent();
    }

    @Override
    public List<Voto> getByVotacion(Votacion votacion) {
        return votosRepository.findByVotacion(votacion);
    }

    @Override
    public long countByVotacion(Votacion votacion) {
        return getByVotacion(votacion).stream().count();
    }

    @Override
    public List<Voto> getByVotacionAndCandidato(Votacion votacion, Candidato candidato) {
        return votosRepository.findByVotacionAndCandidato(votacion, candidato);
    }

    @Override
    public synchronized void registerVote(Voto voto) {
        votosRepository.save(voto);
    }

    @Override
    public void registerVote(Integer idCandidato, String idAprendiz) {
        Candidato candidato = candidatosService.getCandidatoById(idCandidato).get();
        Voto voto = Voto.builder()
                .candidato(candidato)
                .aprendiz(Aprendiz.builder().id(idAprendiz).build())
                .votacion(candidato.getVotacion())
                .valido(true)
                .build();
        votosRepository.save(voto);
    }

    @Override
    public Boolean hasAlreadyVote(String authority) {
        Votacion votacion = votacionesService.getCurrentVotacion().get();
        return hasAlreadyVote(authority, votacion.getId());
    }

}
