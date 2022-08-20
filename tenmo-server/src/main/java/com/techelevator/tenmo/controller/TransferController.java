package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    TransferDao transferDao;

    @RequestMapping(path = "",method = RequestMethod.GET)
    public List<Transfer> getTransfer(){
        return transferDao.getAllTransfers();
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public List<Transfer> getAllTransferById(@PathVariable int userId ){
        return transferDao.getTransfersByUserId(userId);
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public Transfer getTransferById (@PathVariable int userId ){
        return transferDao.getTransferByTransferId(userId);
    }

    @RequestMapping(path ="/{id}",method = RequestMethod.PUT)
    public boolean updateTransfer(@RequestBody Transfer transfer){
        return transferDao.updateTransfer(transfer);
    }

}
