package study.mybatisproject.domain.board.repository;

import org.springframework.stereotype.Repository;
import study.mybatisproject.domain.board.entity.Board;

import java.util.List;

/**
 * 게시판 레파지토리
 * @author jiwon
 */
@Repository
public interface BoardRepository {
    List<Board> getBoardList();
    Board getBoard(int boardId);
    void save(Board board);
    void update(Board board);
    void delete(int boardId);

}
