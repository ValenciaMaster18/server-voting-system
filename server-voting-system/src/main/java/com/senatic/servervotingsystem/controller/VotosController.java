package com.senatic.servervotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senatic.servervotingsystem.service.VotosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/votos")
@RequiredArgsConstructor
public class VotosController {

    private final VotosService votosService;

    @PostMapping("/vote-by/{idCandidato}")
    public ResponseEntity<HttpStatus> saveVoto(@PathVariable("idCandidato") Integer idCandidato,
            Authentication authentication) {
        String idAprendiz = authentication.getName();
        votosService.registerVote(idCandidato, idAprendiz);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/has-already-vote/{idAprendiz}")
    public ResponseEntity<Boolean> handleHasAlreadyVote(@PathVariable("idAprendiz") String idAprendiz) {
        return ResponseEntity.status(HttpStatus.OK).body(votosService.hasAlreadyVote(idAprendiz));
    }

}
