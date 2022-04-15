package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.MatchMapper;
import com.facrod.prodemundial.repository.MatchRepository;
import com.facrod.prodemundial.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Primary
@Service("matchService")
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    @Override
    public MatchResponseDTO getMatch(Long id) throws AppException {
        var match = matchRepository.findById(id).orElseThrow(() -> {
            log.error("Error al buscar partido con id '{}'", id);
            return new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");
        });
        return MatchMapper.toMatchResponseDto(match);
    }

    @Override
    public List<MatchResponseDTO> getMatches() {
        return MatchMapper.toMatchResponseDto(matchRepository.findAll());
    }

}
