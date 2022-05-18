package com.example.demo.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class MarketPostDto {
    @NotBlank(message = "상품의 이름을 입력해 주세요")
    private String ItemName;
    @NotBlank(message = "내용을 입력해 주세요")
    private String ItemBody;
    @NotBlank(message = "카테고리를 설정해 주세요")
    private String category;
    @NotBlank(message = "거래하고 싶은 지역을 입력해 주세요")
    private String location;
    @NotBlank(message = "가격을 입력해 주세요")
    private int price;
    @NotBlank(message = "상품의 이미지를 올려주세요")
    private List<MultipartFile> imageFiles;
}
