package com.example.demo.util;

import com.example.demo.model.MarketBoard;
import com.example.demo.model.UploadFile;
import com.example.demo.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileStore {

    private final MarketRepository marketRepository;

    @Value("${file.dir}")
    private String fileDir;

    public String getSaveFile(String filename){
        return fileDir + filename;
    }

    //이미지 파일 여려장 저장
    @Transactional
    public List<UploadFile> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> saveFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()){
                UploadFile uploadFile = saveFile(multipartFile);
                saveFileResult.add(uploadFile);
            }
        }
        return saveFileResult;
    }

    //이미지 파일 1장 저장
    @Transactional
    public UploadFile saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        //서버에 저장하는 파일명
        String storeFileName = creatStoreFileName(originalFilename);

        multipartFile.transferTo(new File(getSaveFile(storeFileName)));
        return new UploadFile (originalFilename, storeFileName);
    }

    private String creatStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
