package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class jdbcAccountDao implements AccountDao{
    private JdbcTemplate jdbcTemplate;
    //*kanae/jaron
    @Override
    public BigDecimal getBalance(String user) {
        String sql = "select balance from account join tenmo_user on account.user_id = tenmo_user.user_id\n" +
                "where username ILike ?;";
        BigDecimal result = jdbcTemplate.queryForObject(sql,BigDecimal.class, user);

        if (result != null) {
            return result;
        } else {
            return null;
        }
    }

    //*awal/jaron
    public int findIdByAccountId(int accountId) {
        String sql = "SELECT *  FROM account WHERE account_id = ?;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, accountId);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }
    //*awal/jaron
   // public int findDoubleGetBalance(double getBalance) {

//        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, getBalance);
//        if (id != null) {
//            return id;
//        } else {
//            return -1;
//        }
//    }

    private Account mapRowToUser(SqlRowSet rs) {
        Account account = new Account();
        account.setAccount_id(rs.getInt("account_id"));
        account.setUser_id(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
        }


}
