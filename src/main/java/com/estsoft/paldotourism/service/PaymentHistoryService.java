package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.entity.PaymentHistory;
import com.estsoft.paldotourism.repository.PaymentHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistoryService(PaymentHistoryRepository paymentHistoryRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
    }

    // 결제 기록 저장
    public void createPaymentHistory() {

        PaymentHistory paymentHistory = PaymentHistory.builder().paymentStatus(true).build();
        paymentHistoryRepository.save(paymentHistory);

    }

    // 결제 기록 조회(1개)
    public PaymentHistory getPaymentHistory(Integer id) {

        PaymentHistory paymentHistory = paymentHistoryRepository.findById(id).get();

        return paymentHistory;
    }

    // 결제 기록 삭제
    public void deletePaymentHistory(Integer id) {

        PaymentHistory paymentHistory = paymentHistoryRepository.findById(id).get();

        paymentHistoryRepository.deleteById(id);
    }


    // 결제 기록 상태 수정
    @Transactional
    public PaymentHistory updatePaymentHistory (Integer id, Boolean status) {

        PaymentHistory paymentHistory = paymentHistoryRepository.findById(id).get();

        paymentHistory.update(status);

        return paymentHistory;
    }

}
