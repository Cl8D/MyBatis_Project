package study.mybatisproject.domain.board.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class Board {
    private int boardId;
    private BoardType boardType;
    private String title;
    private String content;
    private Date regDate;
}
