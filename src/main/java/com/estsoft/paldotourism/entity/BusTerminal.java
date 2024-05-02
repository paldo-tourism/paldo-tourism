package com.estsoft.paldotourism.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusTerminal extends BaseTime{

    @Id
    private String id;

    private String name;

    private String region;
}
