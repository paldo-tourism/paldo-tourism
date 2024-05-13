package com.estsoft.paldotourism.dto.qna.notification;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationDTO {
    private Long id;
    private String receiver;
    private String message;
    private String type;
    private String createdAt;

    @Builder
    public NotificationDTO(Long id, String receiver, String message, String type, String createdAt) {
        this.id = id;
        this.receiver = receiver;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
    }
}
