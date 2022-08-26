package com.techelevator;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTest extends BaseDaoTests{

    private JdbcTransferDao sut;
    private Transfer firstTransfer;
    private Transfer secondTransfer;
    @Before
    public void setup(){
        sut = new JdbcTransferDao(dataSource);
        firstTransfer = new Transfer(3001,1,2,2001,2002, BigDecimal.valueOf(50));
        secondTransfer = new Transfer(3001,1,2,2002, 2001,BigDecimal.valueOf(100));
    }
    @Test
    public void makeTransfer_creates_new_transfer() {
        Transfer transferToCreate = new Transfer(3003, 1, 1, 2002, 2001, BigDecimal.valueOf(100.00));
        sut.makeTransfer(transferToCreate);
        assertTransfersMatch(transferToCreate, sut.getTransferByTransferId(3003));
    }

    @Test
    public void getTransferByTransferId_returns_correct_transfer() {
        assertTransfersMatch(firstTransfer, sut.getTransferByTransferId(3001));
    }

    @Test
    public void getTransfersByUserId_returns_correct_transfer() {
        List<Transfer> transferList = sut.getTransfersByUserId(1001);
        Assert.assertEquals(0, transferList.size());
    }


    @Test
    public void getAllTransfers_returns_correct_number_of_transfers() {
        List<Transfer> transferList = sut.getAllTransfers();

        Assert.assertEquals(2, transferList.size());
    }
    @Test
    public void getPendingTransfers_returns_correct_number_of_transfers() {
        List<Transfer> transferList = sut.getPendingTransfers(1001);
        Assert.assertEquals(0, transferList.size());
    }

    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        Assert.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        Assert.assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
        Assert.assertEquals(expected.getAccountTo(), actual.getAccountTo());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
    }
}

