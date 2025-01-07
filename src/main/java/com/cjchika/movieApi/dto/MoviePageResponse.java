package com.cjchika.movieApi.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> movies,
                                Integer pageNumber,
                                Integer pageSize,
                                Long totalElements,
                                int totalPages,
                                boolean isLast) {
}
