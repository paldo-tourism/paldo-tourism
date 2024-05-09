package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory,Integer> {
}
