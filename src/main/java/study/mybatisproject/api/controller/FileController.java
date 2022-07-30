package study.mybatisproject.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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



}
