package study.mybatisproject.api.controller;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import study.mybatisproject.api.common.config.exception.ApiException;
import study.mybatisproject.api.common.config.http.ApiResponseCode;
import study.mybatisproject.domain.board.entity.Image;
import study.mybatisproject.domain.board.entity.ThumbnailType;
import study.mybatisproject.domain.board.service.UploadFileService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 업로드된 이미지를 원하는 사이즈로 만들기 (썸네일 만들기)
 */
@Slf4j
@Controller
@RequestMapping("/thumbnail")
public class ThumbnailController {

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/make/{uploadFileId}/{thumbnailType}")
    public void make(@PathVariable int uploadFileId,
                     @PathVariable ThumbnailType thumbnailType,
                     HttpServletResponse response) {
        Image uploadImage = uploadFileService.get(uploadFileId);
        if (uploadImage == null) {
            throw new ApiException(ApiResponseCode.UPLOAD_FILE_IS_NULL);
        }

        String pathname = uploadImage.getPathname();
        File file = new File(pathname);

        if(!file.isFile()) {
            throw new ApiException(ApiResponseCode.UPLOAD_FILE_IS_NULL);
        }

        try {
            int width = thumbnailType.getWidth();
            int height = thumbnailType.getHeight();

            // 썸네일 파일 이름을 보통 a.png -> a130x250.png 이런 식으로 저장하니까 바꿔준 것.
            String thumbnailPathname = pathname
                    .replace(".", "_" + width + "x" + height + ".");
            File thumbnailFile = new File(thumbnailPathname);

            if(!thumbnailFile.isFile()) {
                log.info("thumbnailFile = {}", pathname);
                log.info("thumbnailPathname = {}", thumbnailPathname);
                // resize 해주기!
                Thumbnails.of(pathname)
                        .size(width, height)
                        .toFile(thumbnailPathname);
            }
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            FileCopyUtils.copy(new FileInputStream(thumbnailFile), response.getOutputStream());
        } catch (IOException e) {
            log.error("Exception: ", e);
        }
    }
}
