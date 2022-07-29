package study.mybatisproject.api.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import study.mybatisproject.domain.board.entity.BoardType;

@Data
@NoArgsConstructor
public class BoardRequest {
    private int boardId;
    private BoardType boardType;
    private String title;
    private String content;

    public BoardRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

