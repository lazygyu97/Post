package com.sparta.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass //jpa entity 해당 추상 클래스를 상속할 경우 추상 클래스에 선언한 멤버 변수를 column으로 인식해준다.
@EntityListeners(AuditingEntityListener.class)
//abstract 를 사용하지 않아도 되지 않지만 이것 자체를 entity로 사용할 일이 없을떄는 abstract를 걸어주는게 좋다
public abstract class Timestamped {

    @CreatedDate//Entity 객체가 생성될때 시간을 담아준다.
    @Column(updatable = false)//변경 가능 유무 옵션
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

//    Date : 2023-01-01
//    Time : 20:21:14
//    Timestamp : 2023-01-01 20:21:14.00003030


}