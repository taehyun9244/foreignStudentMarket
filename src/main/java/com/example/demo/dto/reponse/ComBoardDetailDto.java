package com.example.demo.dto.reponse;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Builder
public class ComBoardDetailDto {

    private Long id;
    private String com_title;
    private String com_subtitle;
    private String com_location;
    private String com_country;
    private String com_contents;
    private String username;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;
}
