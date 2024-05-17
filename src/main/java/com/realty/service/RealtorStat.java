package com.realty.service;

import lombok.Data;

@Data
public class RealtorStat {
    String name;
    String surname;
    int count;

    public RealtorStat(String name, String surname, int count){
        this.name = name;
        this.surname = surname;
        this.count = count;
    }
}
