package com.swati.smec.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "events")
public class Event implements Serializable {

    private static final long serialVersionUID = 2905599484400221074L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Event(String eventName, Account account) {
        this.eventName = eventName;
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventName.equals(event.eventName) && dateCreated.equals(event.dateCreated) && account.equals(event.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, dateCreated, account);
    }

}
