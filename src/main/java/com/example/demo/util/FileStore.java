package com.example.demo.util;

import com.example.demo.model.MarketBoard;
import com.example.demo.model.UploadFile;
import com.example.demo.model.User;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<UploadFile> saveFiles(Long marketId, List<MultipartFile> multipartFiles, UserDetailsImpl userDetails) throws IOException {
        User user = userDetails.getUser();
        List<UploadFile> saveFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()){
                UploadFile uploadFile = saveFile(marketId, multipartFile, user);
                saveFileResult.add(uploadFile);
            }
        }
        return saveFileResult;
    }

    @Transactional
    public UploadFile saveFile(Long marketId ,MultipartFile multipartFile, User user) throws IOException {
        MarketBoard findByMarketId = marketRepository.findById(marketId).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않습니다")
        );
        if (multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        //서버에 저장하는 파일명
        String storeFileName = creatStoreFileName(originalFilename);

        multipartFile.transferTo(new File(getSaveFile(storeFileName)));
        return new UploadFile (originalFilename, storeFileName, user, findByMarketId);
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
