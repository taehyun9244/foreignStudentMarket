package com.example.demo.dto.reponse;

import com.example.demo.model.UploadFile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ImageFilesRes {

    private Long id;
    private String fileName;
    private LocalDateTime creatAt;
//    private

    public ImageFilesRes(UploadFile uploadFile) {
        this.id = uploadFile.getId();
        this.fileName = uploadFile.getUploadFileName();
        this.creatAt = uploadFile.getCreatedAt();
    }
}
