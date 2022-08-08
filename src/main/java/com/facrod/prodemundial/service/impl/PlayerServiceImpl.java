package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.enums.PlayerSort;
import com.facrod.prodemundial.repository.PlayerRepository;
import com.facrod.prodemundial.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Primary
@Service("playerService")
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private static final int PAGE_SIZE = 20;

    @Override
    public Page<PlayerResponseDTO> getPlayers(String sortBy, int page) {
        var pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(PlayerSort.fromString(sortBy).value()).descending());
        var pagedResult = playerRepository.findAll(pageable);
        return pagedResult.map(p -> {
            var dto = new PlayerResponseDTO();
            BeanUtils.copyProperties(p, dto);
            return dto;
        });
    }

}
