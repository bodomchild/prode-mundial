package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.TeamService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {

    private final TeamService teamService;
    private final TeamService teamServiceAdmin;

    public TeamController(TeamService teamService, @Qualifier("teamServiceAdmin") TeamService teamServiceAdmin) {
        this.teamService = teamService;
        this.teamServiceAdmin = teamServiceAdmin;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAll() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable String id) throws AppException {
        return ResponseEntity.ok(teamService.getTeam(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Valid TeamDTO team) throws AppException {
        var createdTeam = teamServiceAdmin.createTeam(team);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(createdTeam.getId());
        return ResponseEntity.created(uri).body(createdTeam);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") String id) throws AppException {
        teamServiceAdmin.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

}
