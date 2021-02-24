package com.swati.smec.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "accounts")
public class Account implements Serializable {

    private static final long serialVersionUID = -1863169144922650869L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_name")
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
             events = new HashSet<>();
        }
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
