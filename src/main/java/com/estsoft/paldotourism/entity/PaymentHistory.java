package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 결제 아이디(PK)

    @Enumerated(EnumType.STRING)
    @Column
    private PaymentStatus paymentStatus; // 결제 상태(성공, 실패)

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @Builder
    public PaymentHistory(PaymentStatus paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public void update(PaymentStatus status) {
        paymentStatus = status;
    }
}
