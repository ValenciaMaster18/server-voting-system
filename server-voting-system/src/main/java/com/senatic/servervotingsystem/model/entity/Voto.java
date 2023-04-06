package com.senatic.servervotingsystem.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="votos", indexes = @Index(name="votos_unique", columnList = "idVotacion, idAprendiz", unique = true))
public class Voto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="idCandidato")
    private Candidato candidato;
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="idAprendiz")
    private Aprendiz aprendiz;
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="idVotacion")
    private Votacion votacion;
    @CreationTimestamp
    private LocalDateTime fechaRegistro;
    private Boolean valido;

}
