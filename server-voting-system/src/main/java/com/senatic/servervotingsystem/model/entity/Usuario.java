package com.senatic.servervotingsystem.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.senatic.servervotingsystem.model.entity.enums.AuthorityEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios", indexes = @Index(name="usuarios_unique", columnList = "username", unique = true))
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 25)
    private String username;

    @Column(length = 250)
    private String password;

    @Enumerated(EnumType.STRING)
	private AuthorityEnum authority;

    @ColumnDefault(value = "true")
    private Boolean enabled;

    @ColumnDefault(value = "true")
    private Boolean accountNonLocked;

    @ColumnDefault(value = "true")
    private Boolean credentialsNonExpired;

    @ColumnDefault(value = "true")
    private Boolean accountNonExpired;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime lastModified;
   
}
