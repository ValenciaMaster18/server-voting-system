package com.senatic.servervotingsystem.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.servervotingsystem.model.dto.AprendizDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.enums.EstadoAprendiz;
import com.senatic.servervotingsystem.model.mapper.AprendizMapper;
import com.senatic.servervotingsystem.repository.AprendicesRepository;
import com.senatic.servervotingsystem.service.AprendicesService;
import com.senatic.servervotingsystem.service.UsuariosService;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class AprendicesServiceImpl implements AprendicesService{

    private final AprendicesRepository aprendicesRepository;
    private final UsuariosService usuariosService;
    private final AprendizMapper aprendizMapper;

    @Override
    public Page<Aprendiz> getAprendicesPaginate(Pageable paging) {
        return aprendicesRepository.findAll(paging);
    }    

    @Override
    public void addAprendiz(Aprendiz aprendiz) {
        if (!usuariosService.alreadyExist(aprendiz.getUsuario())) {
            usuariosService.addUsuario(aprendiz.getUsuario());
            aprendicesRepository.save(aprendiz);
        }
    }

    @Override
    public Optional<Aprendiz> findById(String id) {
        return aprendicesRepository.findById(id);
    }

    @Override
    public void updateAprendiz(AprendizDTO aprendizDto) {
        Aprendiz aprendiz = aprendizMapper.dtoToPojo(aprendizDto);
        aprendicesRepository.save(aprendiz);
    }

    @Override
    public Optional<Aprendiz> getAprendizById(String idAprendiz) {
        return aprendicesRepository.findById(idAprendiz);
    }

    @Override
    public void addAprendices(List<Aprendiz> aprendices) {
        aprendices.forEach(aprendiz -> addAprendiz(aprendiz));
    }

    @Override
    public Page<Aprendiz> getAprendicesPaginateByExample(Pageable paging, Example<Aprendiz> example) {
        return aprendicesRepository.findAll(example, paging);
    }

    @Override
    public Integer countAprendicesByEstado(EstadoAprendiz estado) {
        return aprendicesRepository.countByEstado(estado);
    }

    @Override
    public Boolean alreadyExist(AprendizDTO aprendizDTO) {
        return aprendicesRepository.existsById(aprendizDTO.getNumeroDocumento());
    }

    @Override
    public Boolean alreadyExist(String idAprendiz) {
        return aprendicesRepository.existsById(idAprendiz);
    }

    @Override
    public void deleteById(String id) {
        aprendicesRepository.deleteById(id);
    }
    
}
