package com.example.booky.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageableDto<T> {
    private Long totalElements;
    private Integer totalPages;
    private List<T> elements;
}
