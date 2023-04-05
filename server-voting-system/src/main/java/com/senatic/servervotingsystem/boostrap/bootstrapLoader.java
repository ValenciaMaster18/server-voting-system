package com.senatic.servervotingsystem.boostrap;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.senatic.servervotingsystem.model.entity.Usuario;
import com.senatic.servervotingsystem.model.entity.enums.AuthorityEnum;
import com.senatic.servervotingsystem.service.UsuariosService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class bootstrapLoader implements CommandLineRunner {

    private final UsuariosService usuariosService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<Usuario> adminOptional = usuariosService.findByUsername("S3n4AcMin0");
        if(adminOptional.isEmpty()){
            loadAdminUsers();
        }
    }

    void loadAdminUsers(){
        Usuario admin = Usuario.builder()

                .username("S3n4AcMin0")
                .password(passwordEncoder.encode("S3n4AcMin0"))
                .authority(AuthorityEnum.ROLE_ADMINISTRADOR)
                .enabled(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .build();
        usuariosService.addUsuario(admin);
    }
    
}
