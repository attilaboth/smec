package com.swati.smec.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "accounts")
public class Account implements Serializable {

    private static final long serialVersionUID = -1863169144922650869L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Event> events;

    public Account(String accountName) {
        this.accountName = accountName;
        this.events = getEvents();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountName.equals(account.accountName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName);
    }

    public Set<Event> getEvents() {
        if(events == null){
             events = Collections.synchronizedSet(new HashSet<>());
        }
        return events;
    }

}
