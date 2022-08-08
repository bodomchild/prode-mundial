package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.enums.PlayerSort;
import com.facrod.prodemundial.mapper.PlayerMapper;
import com.facrod.prodemundial.pagination.Page;
import com.facrod.prodemundial.repository.PlayerRepository;
import com.facrod.prodemundial.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Primary
@Service("playerService")
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private static final int PAGE_SIZE = 20;

    private final PlayerRepository playerRepository;

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

}
