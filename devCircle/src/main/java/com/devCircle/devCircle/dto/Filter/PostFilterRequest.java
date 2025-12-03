package com.devCircle.devCircle.dto.Filter;

import lombok.Data;

import java.util.List;

@Data
public class PostFilterRequest {
    private String keyword;       // title + description search
    private List<Long> tagIds;    // list of selected tags
    private String status;        // OPEN or CLOSED
}