package com.tekarch.AccountsMS.Services;

import com.tekarch.AccountsMS.Models.Account;
import com.tekarch.AccountsMS.Repositories.AccountRepository;
import com.tekarch.AccountsMS.Services.Interfaces.AccountService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service

public class AccountServiceImpl implements AccountService {
   // private final AccountRepository accountRepository;
   @Autowired
   private AccountRepository accountRepository;

    @Autowired
    private RestTemplate restTemplate;
    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

//    public AccountServiceImpl(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }

    // URL for User Service
    private final String USER_SERVICE_URL = "http://localhost:8080/api/users";

    /**
     * Helper method to validate the userId by calling User Service.
     */
    @Override
    public boolean validateUserId(Long userId) {
        String url = USER_SERVICE_URL + "/" + userId; // User Service URL
        try {
            restTemplate.getForEntity(url, String.class);
            return true; // User exists
        } catch (Exception e) {
            throw new RuntimeException("User ID " + userId + " not found in User Service");
        }
    }

    @Override
    public Account addAccount(Account account) {
        validateUserId(account.getUserId());
       logger.info("new account is with account number is added {} ",account.getAccountNumber());
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        logger.info("Displaying all the accounts");
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(long accountId) {
        return Optional.ofNullable(accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("account not found")));

    }

    @Override
    public Account updateAccount(Long accountId, Account updatedAccount) {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));

        if (updatedAccount.getAccountType() != null) existingAccount.setAccountType(updatedAccount.getAccountType());
        if (updatedAccount.getBalance() != null) existingAccount.setBalance(updatedAccount.getBalance());
        if (updatedAccount.getCurrency() != null) existingAccount.setCurrency(updatedAccount.getCurrency());

        return accountRepository.save(existingAccount);
    }

//    @Override
//    public List<Account> getAccountsByUserId(Long userId) {
//        validateUserId(userId);
//        return accountRepository.findByUserId(userId);
//    }
}
