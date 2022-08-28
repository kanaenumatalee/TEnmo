package com.techelevator;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class JdbcAccountDaoTest extends BaseDaoTests {

    private JdbcAccountDao sut;
    private Account newAccount;
    @Before
    public void setup(){
        sut = new JdbcAccountDao(dataSource);
        newAccount = new Account(2002, 1002, new BigDecimal("1000.00"));
    }
    @Test
    public void getBalance_returns_correct_balance() {
        Assert.assertEquals(new BigDecimal ("1000.00"),sut.getBalance("Mio"));
    }



    @Test
    public void getAccountByAccountID_returns_correct_account() {
        assertAccountsMatch(newAccount, sut.getAccountByAccountId(2002));
    }

    @Test
    public void getAccountByUserID_returns_correct_account() {
        assertAccountsMatch(newAccount, sut.getAccountByUserId(1002));

    }


    private void assertAccountsMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
    }
}


