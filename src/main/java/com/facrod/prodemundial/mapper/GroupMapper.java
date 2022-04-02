package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.GroupDTO;
import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.entity.WCTeam;
import com.facrod.prodemundial.exceptions.AppException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class GroupMapper {

    private GroupMapper() {
    }

    public static List<GroupDTO> getGroupListFromTeamsList(List<WCTeam> teams) {
        var teamsDtos = TeamMapper.toDto(teams);
        var groupNames = teamsDtos.stream().map(TeamDTO::getGroup).collect(Collectors.toSet());
        var groups = new ArrayList<GroupDTO>();

        for (var groupName : groupNames) {
            var group = new GroupDTO();
            group.setGroup(groupName);
            group.setTeams(teamsDtos.stream().filter(team -> team.getGroup().equals(groupName)).collect(Collectors.toList()));
            groups.add(group);
        }

        return groups;
    }

    public static GroupDTO getGroupFromTeamsList(String groupName, List<WCTeam> teams) throws AppException {
        if (teams.isEmpty()) {
            throw new AppException(HttpStatus.NOT_FOUND, "El grupo '" + groupName + "' no existe");
        }

        var teamsDtos = TeamMapper.toDto(teams);
        var group = new GroupDTO();
        group.setGroup(groupName);
        group.setTeams(teamsDtos);

        return group;
    }

}
