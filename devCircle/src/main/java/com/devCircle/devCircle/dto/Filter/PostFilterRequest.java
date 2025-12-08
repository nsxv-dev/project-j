package com.devCircle.devCircle.dto.Filter;

import com.devCircle.devCircle.entity.PostStatus;
import lombok.Data;

import java.util.List;

@Data
public class PostFilterRequest {
    private String keyword;       // title + description search
    private List<Long> tagIds;    // list of selected tags
    private PostStatus status;        // OPEN or CLOSED
}