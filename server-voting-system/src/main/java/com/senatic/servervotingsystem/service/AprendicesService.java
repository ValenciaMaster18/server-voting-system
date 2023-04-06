package com.senatic.servervotingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.senatic.servervotingsystem.model.dto.AprendizDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.enums.EstadoAprendiz;

public interface AprendicesService {
    Page<Aprendiz> getAprendicesPaginate(Pageable paging);
    void addAprendiz(Aprendiz aprendiz);
	Optional<Aprendiz> findById(String id);
	void updateAprendiz(AprendizDTO aprendizDto);
	Optional<Aprendiz> getAprendizById(String idAprendiz);
	void addAprendices(List<Aprendiz> aprendices);
	Page<Aprendiz> getAprendicesPaginateByExample(Pageable paging, Example<Aprendiz> example);
	Integer countAprendicesByEstado(EstadoAprendiz estado);
	Boolean alreadyExist(AprendizDTO aprendizDTO);
	Boolean alreadyExist(String idAprendiz);
    void deleteById(String id);
}
