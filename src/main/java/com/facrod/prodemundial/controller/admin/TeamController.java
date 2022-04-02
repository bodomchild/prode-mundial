package com.facrod.prodemundial.controller.admin;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.TeamService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("teamAdminController")
@RequestMapping("/api/v1/admin/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(@Qualifier("teamServiceAdmin") TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO team) throws AppException {
        var createdTeam = teamService.createTeam(team);
        var adminUriString = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTeam.getId()).toUriString();
        var uri = UriComponentsBuilder.fromUriString(adminUriString.replace("/admin", "")).build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") String id) throws AppException {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

}
