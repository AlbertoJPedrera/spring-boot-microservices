package com.netmind.accountsservice.controller;

import com.netmind.accountsservice.model.Account;
import com.netmind.accountsservice.payload.ApiResponse;
import com.netmind.accountsservice.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Validated
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @Value("${accounts-service.maxtoshow:1}")
    private Integer maxToShow;

    //Create Account
    @PostMapping
    @Operation(summary = "Add a new account", description = "Returns a persisted account")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successfully created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4XX", description = "Bad request")
    })
    public ResponseEntity<Account> createAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Account data")
            @RequestBody @Valid Account account
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(account));
    }

    // Get single Account Details
    @GetMapping("/{id}")
    @Operation(summary = "Get an account by id", description = "Returns an account as per the id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not found - The account was not found")
    })
    public ResponseEntity<Account> getAccount(
            @Parameter(name = "id", description = "Account id", example = "1")
            @PathVariable @Min(1) Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(id));
    }

    // Get Accounts using Customer ID
    @GetMapping("/user/{ownerId}")
    @Operation(summary = "Get a customer accounts", description = "Returns a customer accounts as per customer id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not found - The customer was not found")
    })
    public ResponseEntity<List<Account>> getAccountsUsingCustomerID(
            @Parameter(name = "id", description = "Customer id", example = "1")
            @PathVariable @Min(1) Long ownerId
    ) {
        return ResponseEntity.ok(accountService.getAccountsByOwnerId(ownerId));
    }

    // Get all Account Details

    @GetMapping
    @Operation(summary = "Get all accounts", description = "Returns a list of accounts")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    public ResponseEntity<List<Account>> getAccounts() {
        logger.info("max number of accounts to show: " + maxToShow);
        List<Account> accounts = accountService.getAccounts();
        int max = accounts.size() > maxToShow ? maxToShow : accounts.size();
        return ResponseEntity.ok(accounts.subList(0, max));
    }

    // update account

    @PutMapping("/{id}")
    @Operation(summary = "Update an account", description = "Returns a saved account")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Successfully updated"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4XX", description = "Bad request")
    })
    public ResponseEntity<Account> updateAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Account data")
            @RequestBody @Valid Account account,
            @Parameter(name = "id", description = "Account id", example = "1")
            @PathVariable @Min(1) Long id
    ) {
        account.setId(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.updateAccount(id, account));
    }

    // Add Money
    @PutMapping("/addmoney/{id}")
    @Operation(summary = "Add money to an account", description = "Returns a saved account")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Successfully updated"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4XX", description = "Bad request")
    })
    public ResponseEntity<Account> addMoney(
            @Parameter(name = "id", description = "Account id", example = "1")
            @PathVariable @Min(1) Long id,
            @Parameter(name = "amount", description = "The amount of money to be added", example = "100")
            @RequestParam @Min(1) int amount,
            @Parameter(name = "ownerId", description = "The customer id", example = "10")
            @RequestParam @Min(1) Long ownerId
    ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.addBalance(id, amount, ownerId));
    }

    // withdraw Money
    @PutMapping("/withdraw/{id}")
    @Operation(summary = "Withdraw money from an account", description = "Returns a saved account")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Successfully updated"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4XX", description = "Bad request")
    })
    public ResponseEntity<Account> withdraw(
            @Parameter(name = "id", description = "Account id", example = "1")
            @PathVariable @Min(1) Long id,
            @Parameter(name = "amount", description = "The amount of money to be withdrawn", example = "100")
            @RequestParam @Min(1) int amount,
            @Parameter(name = "ownerId", description = "The customer id", example = "10")
            @RequestParam @Min(1) Long ownerId
    ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.withdrawBalance(id, amount, ownerId));
    }

    // Delete Account

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account", description = "Returns information about the deletion")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Successfully deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4XX", description = "Bad request")
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse deleteAccount(
            @Parameter(name = "id", description = "Account id", example = "1")
            @PathVariable @Min(1) Long id
    ) {
        this.accountService.delete(id);
        return new ApiResponse("Account is Successfully Deleted", true);
    }

    // Delete Account using ownerId

    @DeleteMapping("user/{ownerId}")
    @Operation(summary = "Delete all customer accounts", description = "Returns information about the deletion")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Successfully deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4XX", description = "Bad request")
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse deleteAccountByUserId(
            @Parameter(name = "ownerId", description = "The customer id", example = "10")
            @PathVariable @Min(1) Long ownerId
    ) {
        this.accountService.deleteAccountsUsingOwnerId(ownerId);
        return new ApiResponse(" Accounts with given userId is deleted Successfully", true);

    }

}
