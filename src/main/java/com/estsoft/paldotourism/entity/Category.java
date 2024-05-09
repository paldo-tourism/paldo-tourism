package com.estsoft.paldotourism.entity;

import lombok.Getter;

@Getter
public enum Category {

    CATEGORY_ANNOUNCEMENT("공지사항"),
    CATEGORY_CANCEL_REFUND("취소/환불"),
    CATEGORY_RESERVATION_CHANGE("예약변경"),
    CATEGORY_QA("기타");

    private final String categoryName;
    Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
