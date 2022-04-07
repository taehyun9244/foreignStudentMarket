package com.example.demo.util;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter // 접근제어자인 Private createdAt과 updateAt에 접근하기 위해서
@Setter
@MappedSuperclass // 이것은 생성 수정일자를 모든 엔티티에서 공통으로 가져가야 되므로 추가, 또한 BaseEntity임을 알리기위해서
@EntityListeners(AuditingEntityListener.class)
// 자동적으로 시간을 적용해주며, BaseEntity를 상속받은 곳에서는 AuditingEntityListener를 지정 불필요
public abstract class Timesteamed {
    @CreatedDate // 게시글의 생성시간
    private LocalDateTime createdAt;
    @LastModifiedDate // 게시글의 수정시간
    private LocalDateTime updateAt;


}
