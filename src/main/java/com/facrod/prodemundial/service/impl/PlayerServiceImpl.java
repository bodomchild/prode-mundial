package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.enums.PlayerSort;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.PlayerMapper;
import com.facrod.prodemundial.pagination.Page;
import com.facrod.prodemundial.repository.PlayerRepository;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service("playerService")
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private static final int PAGE_SIZE = 20;

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Override
    public Page<PlayerResponseDTO> getPlayers(String sortBy, int page) {
        var sortProperty = PlayerSort.fromString(sortBy).value();
        var pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(sortProperty).descending());
        var pagedResult = playerRepository.findAll(pageable);
        var playerList = PlayerMapper.toDto(pagedResult.getContent());
        return Page.<PlayerResponseDTO>builder()
                .data(playerList)
                .sort(sortProperty)
                .pageNumber(page)
                .totalPages(pagedResult.getTotalPages())
                .totalElements(pagedResult.getTotalElements())
                .first(pagedResult.isFirst())
                .last(pagedResult.isLast())
                .build();
    }

    @Override
    public List<PlayerResponseDTO> getPlayersByTeam(String teamId) throws AppException {
        if (!teamRepository.existsById(teamId)) {
            throw new AppException(HttpStatus.NOT_FOUND, "Equipo no encontrado");
        }

        var players = playerRepository.findByTeamId(teamId);
        return PlayerMapper.toDto(players);
    }

}
