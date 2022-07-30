package study.mybatisproject.domain.board.entity;

import lombok.Data;

@Data
public class Image {
    private int uploadFileId;
    private String pathname;
    private String filename;
    private String originalFilename;
    private int size;
    private String contentType;
    private String resourcePathname;
}
