package study.mybatisproject.api.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadFile {
    private String pathname;
    private String filename;
    private String originalFilename;
    private int size;
    private String contentType;
    private String resourcePathname;
}
