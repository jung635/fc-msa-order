package dev.practice.order.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@Getter
@MappedSuperclass // 이 class를 상속받는 abstract는 createdAt, updatedAt이 알아서 상속됨
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    //@CreatedDate // localdatetime만 지원하고 zonedDateTime은 지원하지 않음
    @CreationTimestamp
    private ZonedDateTime createdAt;

    //@LastModifiedBy // localdatetime만 지원하고 zonedDateTime은 지원하지 않음
    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}
