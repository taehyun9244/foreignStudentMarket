package com.example.demo.dto.reponse;

import com.example.demo.model.DeliveryBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryBoardDetailResDto {
    private Long id;
    private String title;
    private String contents;
    private String country;
    private int price;
    private String location;
    private String nickname;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    //상세 게시글 조회 데이터 가공
    public static DeliveryBoardDetailResDto of(DeliveryBoard deliveryBoard) {
        return DeliveryBoardDetailResDto.builder()
                .id(deliveryBoard.getId())
                .title(deliveryBoard.getTitle())
                .contents(deliveryBoard.getContents())
                .country(deliveryBoard.getCountry())
                .price(deliveryBoard.getPrice())
                .location(deliveryBoard.getLocation())
                .createdAt(deliveryBoard.getCreatedAt())
                .updateAt(deliveryBoard.getUpdateAt())
                .nickname(deliveryBoard.getUser().getNickname())
                .build();
    }
}
