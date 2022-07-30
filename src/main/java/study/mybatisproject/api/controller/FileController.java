package study.mybatisproject.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import study.mybatisproject.api.common.config.GlobalConfig;
import study.mybatisproject.api.common.config.exception.ApiException;
import study.mybatisproject.api.common.config.http.ApiResponseCode;
import study.mybatisproject.api.controller.dto.ApiResponse;
import study.mybatisproject.api.controller.dto.UploadFile;
import study.mybatisproject.domain.board.service.UploadFileService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@Api(tags = "파일 API")
@Slf4j
public class FileController {
    @Autowired
    private GlobalConfig config;

    @Autowired
    private UploadFileService uploadFileService;

    /**
     * 업로드 리턴
     */
    @PostMapping("/save")
    @ApiOperation(value = "파일 업로드")
    // 여기서 @RequestParam은 <input>의 name이랑 동일하게 둬야 한다.
    public ApiResponse<Boolean> save(@RequestParam(value = "uploadFile") MultipartFile file) {
        log.debug("multipartFile: {}", file);

        // 무조건 파일을 체크하도록 하였기 때문에, 파일이 비어있다면 예외 터트림
        if(file == null || file.isEmpty()) {
            throw new ApiException(ApiResponseCode.DATA_IS_NULL);
        }

        // 사진 저장 시 효율적인 구분을 위해 날짜별로 나누기
        // 저장한 날짜 가져오기.
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        String uploadFilePath = config.getUploadFilePath() + currentDate + "/";
        log.debug("uploadFilePath: {}", uploadFilePath);

        // 파일 이름은 UUID + .확장자
        // 확장자는 원본 파일의 이름에서 . 다음에 있는 값을 가져오도록. (substring)
        String prefix = Objects.requireNonNull(file.getOriginalFilename()).substring(
                file.getOriginalFilename().lastIndexOf(".") + 1,
                file.getOriginalFilename().length());

        String fileName = UUID.randomUUID() + "." + prefix;
        log.info("fileName: {}", fileName);

        // 저장될 상위경로의 폴더가 없다면, 생성해주기.
        File folder = new File(uploadFilePath);
        if(!folder.isDirectory()) {
            folder.mkdirs();
        }

        // 파일의 저장 경로 : 파일이 저장될 상위 경로 (/home/upload) + 파일명의 조합.
        String pathName = uploadFilePath + fileName;
        log.info("pathName: {}", pathName);

        // 리소스 경로
        String resourcePathname = config.getUploadResourcePath() + currentDate + "/" + fileName;

        // dest 파일을 transferTo를 통해 저장한다.
        File dest = new File(pathName);
        log.debug("dest: {}", dest);
        try {
            file.transferTo(dest);

            // 파일 업로드가 완료되면 DB에 저장되도록.
            UploadFile uploadFile = new UploadFile();
            // content type
            uploadFile.setContentType(file.getContentType());
            // 원본 파일명
            uploadFile.setOriginalFilename(file.getOriginalFilename());
            // 저장되는 파일명
            uploadFile.setFilename(fileName);
            // 실제 파일이 저장되는 경로
            uploadFile.setPathname(pathName);
            // 파일의 사이즈
            uploadFile.setSize((int) file.getSize());
            // static resource의 접근 경로 - 브라우저에서 접속할 수 있는 경로.
            uploadFile.setResourcePathname(resourcePathname);
            uploadFileService.save(uploadFile);
        } catch (IOException e) {
            log.error("Exception : ", e);
        }

        return new ApiResponse<Boolean>(true);

    }


}
