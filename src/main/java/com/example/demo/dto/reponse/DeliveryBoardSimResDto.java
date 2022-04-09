package com.example.demo.dto.reponse;

import com.example.demo.model.DeliveryBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class DeliveryBoardSimResDto {
    private Long id;
    private String title;
    private int price;
    private String location;
    private String country;
    private String username;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    //데이터 가공
    public static DeliveryBoardSimResDto of(DeliveryBoard board){
        return DeliveryBoardSimResDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .price(board.getPrice())
                .location(board.getLocation())
                .country(board.getCountry())
                .createdAt(board.getCreatedAt())
                .updateAt(board.getUpdateAt())
                .username(board.getUser().getUsername())
                .build();
    }
    //전체 게시글 조회 데이터 가공
    public static List<DeliveryBoardSimResDto> list(List<DeliveryBoard> boards){
        List<DeliveryBoardSimResDto> deliveryBoardSimResDtos = new ArrayList<>();
        for (DeliveryBoard board : boards){
            deliveryBoardSimResDtos.add(of(board));
        }
        return deliveryBoardSimResDtos;
    }
}
