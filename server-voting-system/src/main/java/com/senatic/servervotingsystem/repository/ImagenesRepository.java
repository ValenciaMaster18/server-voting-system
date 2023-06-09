package com.senatic.servervotingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senatic.servervotingsystem.model.entity.Imagen;

@Repository
public interface ImagenesRepository extends JpaRepository<Imagen, String> {
    
    @Modifying
    @Query(value = "UPDATE imagenes i SET image=:image WHERE i.id=:idImagen", nativeQuery = true)
    void updateImageById(@Param("idImagen")String idImagen, String image);

}
