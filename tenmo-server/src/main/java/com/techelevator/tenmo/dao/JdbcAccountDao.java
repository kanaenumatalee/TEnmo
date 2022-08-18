package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class JdbcAccountDao implements AccountDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //*kanae/jaron
    @Override
    public BigDecimal getBalance(String user) {
        String sql = "SELECT balance FROM account AS a " +
                     "JOIN tenmo_user AS t ON a.user_id = t.user_id " +
                     "WHERE username = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, user);
        Account account = new Account();
        if (result.next()) {
            account = mapRowToAccount(result);
            account.setBalance((BigDecimal) result);
        }
        return account.getBalance();
    }

    //*awal/jaron
    @Override
    public int findIdByAccountId(int accountId) {
        String sql = "SELECT * FROM account WHERE account_id = ?;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, accountId);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public Account getAccountByUserId(int userId) {
        return null;
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        return null;
    }

    @Override
    public void updateAccount(Account account) {

    }

    private Account mapRowToAccount(SqlRowSet rs) {
        int accountId = rs.getInt("account_id");
        int userAccountId = rs.getInt("user_id");
        BigDecimal balance = rs.getBigDecimal("balance");

        //Account account = new Account();
        //account.setAccount_id(rs.getInt("account_id"));
        //account.setUser_id(rs.getInt("user_id"));
        // account.setBalance(rs.getBigDecimal("balance"));
        Account account = new Account(accountId, userAccountId, balance);
        return account;
        }


}
