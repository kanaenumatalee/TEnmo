package com.techelevator;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcTransferStatusDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

public class JdbcTransferStatusDaoTest extends BaseDaoTests {


    private JdbcTransferStatusDao sut;
    private TransferStatus newStatus;

    @Before
    public void setup(){
        sut = new JdbcTransferStatusDao(dataSource);
        newStatus = new TransferStatus(2, "Approved");
    }
    @Test
    public void getTransferStatusByDesc_returns_correct_transfer_status() {
        assertTransferStatusesMatch(newStatus, sut.getTransferStatusByDesc("Approved"));
    }

    @Test
    public void getTransferStatusById_returns_correct_transfer_status() {
        assertTransferStatusesMatch(newStatus, sut.getTransferStatusById(2));
    }


    private void assertTransferStatusesMatch(TransferStatus expected, TransferStatus actual) {
        Assert.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        Assert.assertEquals(expected.getTransferStatusDesc(), actual.getTransferStatusDesc());

    }
}
