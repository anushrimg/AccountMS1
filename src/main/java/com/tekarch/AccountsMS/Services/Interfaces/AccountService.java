package com.tekarch.AccountsMS.Services.Interfaces;

import com.tekarch.AccountsMS.Models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account addAccount(Account account);
    List<Account> getAllAccounts();
    Optional<Account> findById(long accountId);

    boolean validateUserId(Long userId);
    Account updateAccount(Long accountId, Account updatedAccount);
   // List<Account> getAccountsByUserId(Long userId);
}
