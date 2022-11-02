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

import javax.sql.DataSource;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcAccountDao implements AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(DataSource datasource){this.jdbcTemplate = new JdbcTemplate(datasource);}

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
        String sql = "SELECT user_id FROM account WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        int userId = 0;
        if(result.next()) {
            userId = result.getInt("user_id");
        }
        return userId;
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
        String sql = "UPDATE account " +
                     "SET balance = ? " +
                     "WHERE account_id = ?";
        jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
    }

    private Account mapResultsToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setUserId(result.getInt("user_id"));
        account.setAccountId(result.getInt("account_id"));
        account.setBalance(result.getBigDecimal("balance"));
        return account;
    }
}
