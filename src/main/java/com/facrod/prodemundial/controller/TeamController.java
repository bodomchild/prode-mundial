package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAll() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable String id) throws AppException {
        return ResponseEntity.ok(teamService.getTeam(id));
    }

}
