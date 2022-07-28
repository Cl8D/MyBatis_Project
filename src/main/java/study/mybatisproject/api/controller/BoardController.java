package study.mybatisproject.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.mybatisproject.api.controller.dto.BoardRequest;
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
@Api(tags = "게시판 API")
public class BoardController {
    private final BoardService boardService;

    /**
     * 게시글 여러 건 조회
     */
    @GetMapping
    @ApiOperation(value = "게시글 여러 건 조회", notes = "게시글의 전체 목록을 조회할 수 있습니다.")
    public List<Board> getBoardList() {
        return boardService.getBoardList();
    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/{boardId}")
    @ApiOperation(value = "게시글 조회", notes = "게시글 번호에 해당하는 상세 정보를 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", example = "1", dataTypeClass = int.class)
    })
    public Board getBoard(@PathVariable int boardId) {
        return boardService.getBoard(boardId);
    }

    /**
     * 게시글 저장 및 수정
     */
    @PutMapping("/save")
    @ApiOperation(value = "게시글 저장 / 수정", notes = "신규 게시글을 저장하거나 기존 게시글을 수정할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", example = "1", dataTypeClass = int.class),
            @ApiImplicitParam(name = "title", value = "제목", example = "title", dataTypeClass = String.class),
            @ApiImplicitParam(name = "content", value = "내용", example = "content", dataTypeClass = String.class)
    })
    public int save(BoardRequest board) {
        boardService.save(board);
        return board.getBoardId();
    }


    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{boardId}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 번호와 일치하는 게시글을 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", example = "1", dataTypeClass = int.class),
    })
    public boolean delete(@PathVariable int boardId) {
        Board findBoard = boardService.getBoard(boardId);
        if(findBoard == null) {
            return false;
        }
        boardService.delete(boardId);
        return true;
    }
}
