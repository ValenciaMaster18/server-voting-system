package com.senatic.servervotingsystem.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.senatic.servervotingsystem.model.entity.enums.EstadoCandidato;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString @EqualsAndHashCode
@Entity
@Table(name="candidatos", indexes = @Index(name="candidatos_unique", columnList = "idAprendiz", unique = true))
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "idAprendiz")
    private Aprendiz aprendiz;

    @OneToOne(cascade=CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name="idImagen")
    private Imagen imagen;

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name="idVotacion")
    private Votacion votacion;

    @Column(length = 250)
    private String propuestas;

    @Enumerated(EnumType.STRING)
    private EstadoCandidato estado;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime lastModified;

}