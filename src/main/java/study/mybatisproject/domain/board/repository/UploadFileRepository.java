package study.mybatisproject.domain.board.repository;

import org.springframework.stereotype.Repository;
import study.mybatisproject.api.controller.dto.UploadFile;
import study.mybatisproject.domain.board.entity.Image;

@Repository
public interface UploadFileRepository {
    void save(UploadFile uploadFile);
    Image get(int uploadFileId);
}
