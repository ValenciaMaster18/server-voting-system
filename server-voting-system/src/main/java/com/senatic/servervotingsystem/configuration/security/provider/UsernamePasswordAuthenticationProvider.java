package com.senatic.servervotingsystem.configuration.security.provider;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.senatic.servervotingsystem.configuration.security.service.AppUserDetailsService;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.service.VotacionesService;
import com.senatic.servervotingsystem.service.VotosService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

  private final PasswordEncoder passwordEncoder;
  private final AppUserDetailsService appUserDetails;
  private final VotosService votosService;
  private final VotacionesService votacionesService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    try {

      UserDetails user = appUserDetails.loadUserByUsername(username);

      if (passwordEncoder.matches(password, user.getPassword())) {

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getAuthorities().stream()
            .findFirst()
            .get().toString()));

        Optional<Votacion> currentVotacion = votacionesService.getCurrentVotacion();

        if (user.isEnabled()) {

          if (user.getAuthorities().toString().contains("ROLE_ADMINISTRADOR")) {

            return new UsernamePasswordAuthenticationToken(username, password, authorities);

          } else if (!votosService.hasAlreadyVote(username, currentVotacion.get().getId())
              && currentVotacion.isPresent()
              && user.getAuthorities().toString().contains("ROLE_APRENDIZ")) {
            System.out.println("Aprendiz que ingresa a votar: " + user.toString()); 
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
            
          } else {
            throw new UsernameNotFoundException("Not votacion avaliable");
          }
        } else {
          throw new UsernameNotFoundException("Account is disabled");
        }
      } else {
        throw new BadCredentialsException("Bad credentials");
      }

    } catch (UsernameNotFoundException | BadCredentialsException ex) {
      throw new BadCredentialsException(ex.getMessage());
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
      return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

}
