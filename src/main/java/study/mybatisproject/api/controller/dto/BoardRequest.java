package study.mybatisproject.api.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardRequest {
    private int boardId;
    private String title;
    private String content;

    public BoardRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

