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

public class jdbcAccountDao implements AccountDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //*kanae/jaron
    @Override
    public BigDecimal getBalance(String user) {
        String sql = "select balance from account join tenmo_user on account.user_id = tenmo_user.user_id\n" +
                "where username = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,user);
        Account account = new Account();
        BigDecimal balance = null;
        if (result.next()){
            balance=result.getBigDecimal("balance");
            account.setBalance(balance);
        }
        return balance;
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

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("account_id"));
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("balance"));
        return user;
        }


}
