package com.senatic.servervotingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.senatic.servervotingsystem.model.entity.Votacion;

@Repository
public interface VotacionesRepository extends JpaRepository<Votacion, Integer> {
    
    @Modifying
    @Query(value="UPDATE votaciones c SET c.estado = 'INHABILITADA' WHERE c.id= :idVotacion" , nativeQuery = true)
    void disableVotacionById(Integer idVotacion);
    
    @Modifying
    @Query(value="UPDATE votaciones c SET c.estado = 'HABILITADA' WHERE c.id= :idVotacion" , nativeQuery = true)
    void enableVotacionById(Integer idVotacion);

    @Modifying
    @Query(value="UPDATE votaciones c SET c.currentVotacion = True WHERE c.id= :idVotacion" , nativeQuery = true)
    void setCurrentById(Integer idVotacion);

    @Modifying
    @Query(value="UPDATE votaciones c SET c.currentVotacion = False WHERE c.id= :idVotacion" , nativeQuery = true)
    void setNotCurrentById(Integer idVotacion);

    Optional<Votacion> findByNombreAndDescripcion(String nombre, String descripcion);

    Optional<Votacion> findByCurrentVotacion(boolean currentVotacion);

}
