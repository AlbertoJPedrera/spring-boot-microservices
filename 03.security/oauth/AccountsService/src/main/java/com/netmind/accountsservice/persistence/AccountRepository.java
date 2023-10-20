package com.netmind.accountsservice.persistence;

import com.netmind.accountsservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByOwnerId(Long OwnerId);
}
