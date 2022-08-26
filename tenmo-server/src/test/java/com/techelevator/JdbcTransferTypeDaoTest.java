package com.techelevator;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcTransferTypeDao;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JdbcTransferTypeDaoTest extends BaseDaoTests {

    private JdbcTransferTypeDao sut;
    private TransferType newType;
    @Before
    public void setup(){
        sut = new JdbcTransferTypeDao(dataSource);
        newType = new TransferType(1, "Request");
    }
    @Test
    public void getTransferTypeById_returns_correct_transfer_status() {
        assertTransferTypesMatch(newType, sut.getTransferTypeById(1));
    }

    @Test
    public void getTransferTypeByDescription_returns_correct_transfer_status() {
        assertTransferTypesMatch(newType, sut.getTransferTypeByDescription("Request"));
    }


    private void assertTransferTypesMatch(TransferType expected, TransferType actual) {
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        Assert.assertEquals(expected.getTransferTypeDescription(), actual.getTransferTypeDescription());

    }

}
