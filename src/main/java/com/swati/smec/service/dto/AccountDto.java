package com.swati.smec.service.dto;

import com.swati.smec.entity.Event;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {

    private String accountName;

    private Set<Event> events;
}
