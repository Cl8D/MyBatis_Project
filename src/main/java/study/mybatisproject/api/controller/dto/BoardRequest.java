package study.mybatisproject.api.controller.dto;

import lombok.Data;

@Data
public class BoardRequest {
    private int boardId;
    private String title;
    private String content;
}

