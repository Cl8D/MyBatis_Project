package study.mybatisproject.domain.board.entity;

/**
 * 게시판 종류 (board_type)
 */
// 인터페이스를 구현한 enum 클래스를 만들 수 있다는 점...!
public enum BoardType implements BaseCodeLabelEnum {
    NOTICE("공지사항"),
    FAQ("자주 묻는 질문"),
    INQUIRY("1:1 문의");

    private String code; // NOTICE
    private String label; // 공지사항

    BoardType(String label) {
        // name()은 호출된 값의 이름을 String으로 리턴한다.
        // 여기서는 name()이 호출한 NOTICE, FAQ, INQUERY 중 하나가 된다.
        this.code = name();
        this.label = label; // label은 생성자로 지정한 값.
    }
    @Override
    public String code() {
        return code;
    }

    @Override
    public String label() {
        return label;
    }
}
