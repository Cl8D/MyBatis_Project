package study.mybatisproject.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.mybatisproject.api.controller.dto.BoardRequest;
import study.mybatisproject.domain.board.entity.Board;
import study.mybatisproject.domain.board.repository.BoardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게시판 서비스
 * @author jiwon
 */
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * 게시글 여러 건 조회
     */
    public List<Board> getBoardList() {
        return boardRepository.getBoardList();
    }

    /**
     * 게시글 단건 조회
     */
    public Board getBoard(int boardId) {
        return boardRepository.getBoard(boardId);
    }

    /**
     * 게시글 저장 / 수정
     */
    public void  save(BoardRequest board) {
        Board findBoard = boardRepository.getBoard(board.getBoardId());
        if(findBoard == null) {
            boardRepository.save(board);
        }
        else {
            boardRepository.update(board);
        }
    }

    /**
     * 게시글 삭제
     */
    public void delete(int boardId) {
        boardRepository.delete(boardId);
    }

    /**
     * 단순 반복문을 통한 등록 처리
     */
    public void saveList1(List<BoardRequest> list) {
        for (BoardRequest board : list) {
            boardRepository.save(board);
        }
    }

    /**
     * 100개씩 배열에 담아서 일괄 등록 처리
     */
    public void saveList2(List<BoardRequest> boardList) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("boardList", boardList);
        boardRepository.saveList(paramMap);
    }
}
