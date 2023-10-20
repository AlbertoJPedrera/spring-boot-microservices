package com.netmind.accountsservice.services;

import com.netmind.accountsservice.exception.AccountNotfoundException;
import com.netmind.accountsservice.model.Account;
import com.netmind.accountsservice.model.Customer;
import com.netmind.accountsservice.persistence.AccountRepository;
import com.netmind.accountsservice.proxy.CustomersServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomersServiceClient customersServiceClient;

    @Override
    public Account create(Account account) {
        Date current_Date = new Date();
        account.setOpeningDate(current_Date);
        Customer owner = customersServiceClient.getCustomer(account.getOwnerId()); // Will be gotten from user service
        account.setOwner(owner);
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        Customer owner = customersServiceClient.getCustomer(account.getOwnerId()); // Will be gotten from user service
        account.setOwner(owner);
        return account;
    }

    @Override
    public List<Account> getAccountsByOwnerId(Long ownerId) {
        return accountRepository.findByOwnerId(ownerId);
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        Account theAccount = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        Customer owner = customersServiceClient.getCustomer(account.getOwnerId()); // Will be gotten from user service
        theAccount.setOwner(owner);
        theAccount.setType(account.getType());
        return accountRepository.save(theAccount);
    }

    @Override
    public Account addBalance(Long id, int amount, Long ownerId) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        Customer owner = customersServiceClient.getCustomer(account.getOwnerId()); // Will be gotten from user service
        account.setOwner(owner);
        int newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    @Override
    public Account withdrawBalance(Long id, int amount, Long ownerId) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        Customer owner = customersServiceClient.getCustomer(account.getOwnerId()); // Will be gotten from user service
        account.setOwner(owner);
        int newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        this.accountRepository.delete(account);
    }

    @Override
    public void deleteAccountsUsingOwnerId(Long ownerId) {
        List<Account> accounts = accountRepository.findByOwnerId(ownerId);
        for (Account account : accounts) {
            this.accountRepository.delete(account);
        }
    }


}
