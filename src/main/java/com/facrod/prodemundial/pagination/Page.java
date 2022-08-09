package com.facrod.prodemundial.pagination;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Page<T> {

    private List<T> data;
    private String sort;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private boolean last;

}
