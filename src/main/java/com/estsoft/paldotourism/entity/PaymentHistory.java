package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 결제 아이디(PK)

    @Column
    private Boolean paymentStatus; // 결제 상태(성공, 실패)

    @Builder
    public PaymentHistory(Boolean paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public void update(Boolean status) {
        paymentStatus = status;
    }
}
