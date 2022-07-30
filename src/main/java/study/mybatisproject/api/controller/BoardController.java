package study.mybatisproject.api.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import study.mybatisproject.api.common.config.exception.ApiException;
import study.mybatisproject.api.common.config.http.ApiResponseCode;
import study.mybatisproject.api.common.config.web.bind.annotation.RequestConfig;
import study.mybatisproject.api.common.framework.paging.domain.PageRequest;
import study.mybatisproject.api.common.framework.paging.domain.PageRequestParam;
import study.mybatisproject.api.common.log.annotation.ExecutionTime;
import study.mybatisproject.api.controller.dto.ApiResponse;
import study.mybatisproject.api.controller.dto.BoardRequest;
import study.mybatisproject.api.controller.dto.BoardSearch;
import study.mybatisproject.domain.board.entity.Board;
import study.mybatisproject.domain.board.entity.BoardType;
import study.mybatisproject.domain.board.service.BoardService;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 컨트롤러
 * @author jiwon
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Api(tags = "게시판 API")
@Slf4j
public class BoardController {
    private final BoardService boardService;

    /**
     * 게시글 여러 건 조회
     */
    @GetMapping
    @ApiOperation(value = "게시글 여러 건 조회", notes = "게시글의 전체 목록을 조회할 수 있습니다.")
    public ApiResponse<List<Board>> getBoardList(
            // 보통 MyBatis에서 List를 조회할 때는 사용할 파라미터 정보를 Object(Class)나 Map을 많이 사용한다고 한다.
            // 근데 뭐 이런 식으로 클래스별로 나눠줄 수도 있다.
            @ApiParam BoardSearch boardSearch,
            @ApiParam PageRequest pageRequest) {

        log.info("pageRequest : {}", pageRequest);

        // PageRequestParam => pageRequest, <T>paramter. 여기서 <T> = BoardSearch.
        // pageRequest => page, size, limit, offset.
        PageRequestParam<BoardSearch> pageRequestParam
                = new PageRequestParam<>(pageRequest, boardSearch);
        return new ApiResponse<>(boardService.getBoardList(pageRequestParam));
    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/{boardId}")
    @ApiOperation(value = "게시글 조회", notes = "게시글 번호에 해당하는 상세 정보를 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", example = "1", dataTypeClass = int.class)
    })
    public ApiResponse<Board> getBoard(@PathVariable int boardId) {
        Board board = boardService.getBoard(boardId);
        if (board == null) {
            throw new ApiException(ApiResponseCode.DATA_IS_NULL,
                    new String[]{String.valueOf(boardId), "번 게시글"});
        }
        return new ApiResponse<>(board);
    }

    /**
     * 게시글 저장 및 수정
     */
    @PutMapping("/save")
    // 로그인 체크가 필요하다면 활성화하기
//    @RequestConfig(loginCheck = true)
    @ApiOperation(value = "게시글 저장 / 수정", notes = "신규 게시글을 저장하거나 기존 게시글을 수정할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", example = "1", dataTypeClass = int.class),
            @ApiImplicitParam(name = "boardType", value = "게시글 유형", example = "NOTICE", dataTypeClass = BoardType.class),
            @ApiImplicitParam(name = "title", value = "제목", example = "title", dataTypeClass = String.class),
            @ApiImplicitParam(name = "content", value = "내용", example = "content", dataTypeClass = String.class),
            @ApiImplicitParam(name = "delYn", value = "삭제 여부", example = "false", dataTypeClass = Boolean.class)
    })
    public ApiResponse<Integer> save(BoardRequest board) {
        // 제목 필수 체크
        if(ObjectUtils.isEmpty(board.getTitle())) {
            throw new ApiException(ApiResponseCode.VALIDATE_REQUIRED,
                    new String[] {"title", "제목"});
        }
        // 내용 필수 체크
        if(ObjectUtils.isEmpty(board.getContent())) {
            throw new ApiException(ApiResponseCode.VALIDATE_REQUIRED,
                    new String[] {"content", "내용"});
        }
        boardService.save(board);
        return new ApiResponse<>(board.getBoardId());
    }


    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{boardId}")
    @RequestConfig(loginCheck = true)
    @ApiOperation(value = "게시글 삭제", notes = "게시글 번호와 일치하는 게시글을 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", example = "1", dataTypeClass = int.class),
    })
    public ApiResponse<Boolean> delete(@PathVariable int boardId) {
        Board findBoard = boardService.getBoard(boardId);
        if(findBoard == null) {
            return new ApiResponse<>(false);
        }
        boardService.delete(boardId);
        return new ApiResponse<>(true);
    }

    /**
     * 대용량 등록 처리 - 단순 반복문
     */
    @ApiOperation(value = "대용량 등록처리 방법 1", notes = "단순 반복문")
    @PutMapping("/saveList1")
    @ExecutionTime // 실행시간 측정
    public ApiResponse<Boolean> saveList1() {
        int count = 0;
        List<BoardRequest> list = new ArrayList<>();
        createBoardDummyData(count, list);
        boardService.saveList1(list);
        return new ApiResponse<>(true);

        // 실행 시간 측정 결과
        // [logExecutionTime:22] - Execution Time = StopWatch '': running time = 100327291200 ns
        // = 약 100초 정도 걸렸다.
    }

    /**
     * 대용량 등록 처리 - 100개씩 묶어서 처리
     */
    @PutMapping("/saveList2")
    @ApiOperation(value = "대용량 등록처리 방법 2", notes = "100개씩 묶어서 처리")
    @ExecutionTime
    public ApiResponse<Boolean> saveList2() {
        int count = 0;
        List<BoardRequest> list = new ArrayList<>();
        createBoardDummyData(count, list);
        boardService.saveList2(list);
        return new ApiResponse<>(true);

        // 실행시간 측정 결과
        // [logExecutionTime:22] - Execution Time = StopWatch '': running time = 680414200 ns
        // 약 0.68초 정도 걸렸다. 훨씬 빠른 걸 확인할 수 있다.
        // 대용량 처리는 이 방법을 쓰는 게 좋을 것 같다.
    }

    private void createBoardDummyData(int count, List<BoardRequest> list) {
        while(true) {
            count++;
            String title = RandomStringUtils.randomAlphabetic(10);
            String content = RandomStringUtils.randomAlphabetic(10);
            list.add(new BoardRequest(title, content));
            // 랜덤하게 10000건의 데이터 생성해주기
            if (count >= 10000) {
                break;
            }
        }
    }
}
