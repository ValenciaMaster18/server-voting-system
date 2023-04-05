package com.senatic.servervotingsystem.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.senatic.servervotingsystem.model.entity.enums.EstadoAprendiz;
import com.senatic.servervotingsystem.model.entity.enums.TipoDocumento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name="aprendices", indexes = @Index(name="aprendices_unique", columnList = "correoElectronico, celular", unique = true))
public class Aprendiz {
    
    @Id
    @Column(length = 25)
    private String id;

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @OneToOne
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    @Column(length = 15)
    private String celular;

    @Column(length = 250)
    private String correoElectronico;

    @Column(length = 20)
    private String ficha;

    @Column(length = 250)
    private String programa;

    @Enumerated(EnumType.STRING)
    private EstadoAprendiz estado;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime lastModified;
}