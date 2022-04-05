package com.facrod.prodemundial.controller.admin;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.TeamService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController("teamAdminController")
@RequestMapping("/api/v1/admin/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(@Qualifier("teamServiceAdmin") TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Valid TeamDTO team) throws AppException {
        var createdTeam = teamService.createTeam(team);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api/v1/team/{id}").build(createdTeam.getId());
        return ResponseEntity.created(uri).body(createdTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") String id) throws AppException {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

}
