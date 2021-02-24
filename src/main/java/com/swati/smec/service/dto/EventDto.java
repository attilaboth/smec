package com.swati.smec.service.dto;

import com.swati.smec.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class EventDto {

    private String eventName;

    private Account account;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventDto{");
        sb.append("eventName='").append(eventName).append('\'');
        sb.append(", account=").append(account);
        sb.append('}');
        return sb.toString();
    }
}
