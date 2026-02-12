package com.example.schedulerdevelop.global.dto;

import org.springframework.data.domain.Page;

import java.util.List;
//페이지 간략화
public record PageResponse<T>(
        List<T> content,
        int pageNumber,
        int totalPages,
        long totalElements,
        boolean isLast
) {
    public PageResponse(Page<T> page) {
        this(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isLast()
        );
    }
}
