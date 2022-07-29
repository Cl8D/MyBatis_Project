package study.mybatisproject.api.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import study.mybatisproject.domain.board.entity.BoardType;

import java.util.List;

/**
 * 게시글 검색 DTO
 */
@Data
@NoArgsConstructor
public class BoardSearch {
    private String keyword;
    private List<BoardType> boardTypes;
}
