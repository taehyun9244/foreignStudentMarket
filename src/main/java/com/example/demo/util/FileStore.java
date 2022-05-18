package com.example.demo.util;

import com.example.demo.model.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getSaveFile(String filename){
        return fileDir + filename;
    }

    //이미지 파일 여려장 저장
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
