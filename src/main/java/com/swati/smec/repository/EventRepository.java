package com.swati.smec.repository;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Boolean> findByAccount(Account account);
}
