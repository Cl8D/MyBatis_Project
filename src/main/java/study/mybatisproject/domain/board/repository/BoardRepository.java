package study.mybatisproject.domain.board.repository;

import org.springframework.stereotype.Repository;
import study.mybatisproject.api.controller.dto.BoardRequest;
import study.mybatisproject.domain.board.entity.Board;

import java.util.List;
import java.util.Map;

/**
 * 게시판 레파지토리
 * @author jiwon
 */
@Repository
public interface BoardRepository {
    List<Board> getBoardList();
    Board getBoard(int boardId);
    void save(BoardRequest board);
    void update(BoardRequest board);
    void delete(int boardId);

    void saveList(Map<String, Object> paramMap);
}
