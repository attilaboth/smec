package com.swati.smec.service.dto;

import lombok.*;

import java.time.LocalDate;

@Data
public class EventStat {

    private LocalDate day;
    private String eventType;
    private Long count;

    public EventStat(LocalDate day) {
        this.day = day;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(day);
        sb.append(", ").append(eventType);
        sb.append(", ").append(count);
        return sb.toString();
    }
}
