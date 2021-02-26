package com.swati.smec.service.dto;

import com.swati.smec.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {

    private String accountName;

    private Set<Event> events;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountDto{");
        sb.append("accountName='").append(accountName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
