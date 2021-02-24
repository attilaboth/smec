package com.swati.smec.repository;

import com.swati.smec.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Boolean> findByAccountName(String name);

}
