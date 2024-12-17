package com.tekarch.AccountsMS.Controller;

import com.tekarch.AccountsMS.Models.Account;
import com.tekarch.AccountsMS.Services.AccountServiceImpl;
import com.tekarch.AccountsMS.Services.Interfaces.AccountService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")

public class AccountController {
    private final AccountService accountService;
    private static final Logger logger = LogManager.getLogger(AccountController.class);

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping
    public ResponseEntity<List<Account>> getAccount(){
        logger.info("Getting all the Accounts");
        return new ResponseEntity<>(accountService.getAllAccounts(),HttpStatus.OK) ;
    }
    @PostMapping
    public ResponseEntity<Account >addAccount(@RequestBody Account account){
        logger.info("Creating a new account {}", account.getAccountNumber());
        return  new ResponseEntity<>(accountService.addAccount(account),HttpStatus.CREATED);
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<Optional<Account>> findById(@PathVariable long accountId){
        return  ResponseEntity.ok(accountService.findById(accountId));
    }
    @PutMapping("/{accountId}")
    public ResponseEntity<Account> update(@PathVariable long accountId,@RequestBody Account updatedAccount) {
        return ResponseEntity.ok(accountService.updateAccount(accountId, updatedAccount));
    }

    @ExceptionHandler
    public ResponseEntity<String> repondWithError(Exception e){
         logger.error("Exception occured Details :{}",e.getMessage());
        return new ResponseEntity<>("Exception Occured.More Info : "+e.getMessage(), HttpStatus.BAD_REQUEST);

    }
}
