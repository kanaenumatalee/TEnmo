package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component

public class JdbcAccountDao implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BigDecimal getBalance(String user) {
        String sql = "SELECT balance FROM account JOIN tenmo_user " +
                     "ON account.user_id = tenmo_user.user_id " +
                     "WHERE username = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, user);
        Account account = new Account();
        BigDecimal balance = null;
        if (result.next()) {
            balance = result.getBigDecimal("balance");
            account.setBalance(balance);
        }
        return balance;
    }

    @Override
    public int findIdByAccountId(int accountId) {
        return 0;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        Account account = null;
        if(result.next()) {
            account = mapResultsToAccount(result);
        }
        return account;
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        Account account = null;
        if(result.next()) {
            account = mapResultsToAccount(result);
        }
        return account;
    }

    @Override
    public void updateAccount(Account account) {
        String sql = "UPDATE accounts " +
                     "SET balance = ? " +
                     "WHERE account_id = ?";

        jdbcTemplate.update(sql, account.getBalance(), account.getAccount_id());
    }

    private Account mapResultsToAccount(SqlRowSet result) {
        int accountId = result.getInt("account_id");
        int userAccountId = result.getInt("user_id");
        Account account = new Account();
        BigDecimal balance = result.getBigDecimal("balance");
        return new Account(accountId, userAccountId, balance);
    }



}