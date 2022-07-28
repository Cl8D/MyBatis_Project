package study.mybatisproject.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.mybatisproject.domain.board.entity.Board;
import study.mybatisproject.domain.board.service.BoardService;

import java.util.List;

/**
 * 게시판 컨트롤러
 * @author jiwon
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    /**
     * 게시글 여러 건 조회
     */
    @GetMapping
    public List<Board> getBoardList() {
        return boardService.getBoardList();
    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/{boardId}")
    public Board getBoard(@PathVariable int boardId) {
        return boardService.getBoard(boardId);
    }

    /**
     * 게시글 저장 및 수정
     */
    @GetMapping("/save")
    public int save(Board board) {
        boardService.save(board);
        return board.getBoardId();
    }

    /**
     * 게시글 삭제
     */
    @GetMapping("/delete/{boardId}")
    public boolean delete(@PathVariable int boardId) {
        Board findBoard = boardService.getBoard(boardId);
        if(findBoard == null) {
            return false;
        }
        boardService.delete(boardId);
        return true;
    }
}
