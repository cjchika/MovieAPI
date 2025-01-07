package com.cjchika.movieApi.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> movieDtoList,
                                Integer pageNumber,
                                Integer pageSize,
                                int totalElements,
                                int totalPages,
                                boolean isLast) {
}
