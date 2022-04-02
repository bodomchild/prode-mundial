package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.GroupDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.GroupMapper;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final TeamRepository teamRepository;

    @Override
    public List<GroupDTO> getAllGroups() {
        var sort = Sort.by(Sort.Order.asc("worldCupGroup"), Sort.Order.desc("points"));
        var teams = teamRepository.findAll(sort);
        return GroupMapper.getGroupListFromTeamsList(teams);
    }

    @Override
    public GroupDTO getGroup(String group) throws AppException {
        var sort = Sort.by(Sort.Order.desc("points"));
        var teams = teamRepository.findByWorldCupGroup(group, sort);
        return GroupMapper.getGroupFromTeamsList(group, teams);
    }
}
