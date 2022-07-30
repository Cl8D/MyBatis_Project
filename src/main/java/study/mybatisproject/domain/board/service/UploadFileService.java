package study.mybatisproject.domain.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.mybatisproject.api.controller.dto.UploadFile;
import study.mybatisproject.domain.board.entity.Image;
import study.mybatisproject.domain.board.repository.UploadFileRepository;

@Service
public class UploadFileService {
    @Autowired
    private UploadFileRepository uploadFileRepository;

    public void save(UploadFile uploadFile) {
        uploadFileRepository.save(uploadFile);
    }

    public Image get(int uploadFileId) {
        return uploadFileRepository.get(uploadFileId);
    }
}
