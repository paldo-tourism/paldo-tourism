package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory,Integer> {
}
