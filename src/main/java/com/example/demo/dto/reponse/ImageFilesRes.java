package com.example.demo.dto.reponse;

import com.example.demo.model.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageFilesRes {

    private Long id;
    private Long marketBoardId;
    private String itemName;
    private LocalDateTime creatAt;

    public ImageFilesRes(UploadFile uploadFile) {
        this.id = uploadFile.getId();
        this.marketBoardId = uploadFile.getMarketBoard().getId();
        this.itemName = uploadFile.getMarketBoard().getItemName();
        this.creatAt = uploadFile.getCreatedAt();
    }
}
