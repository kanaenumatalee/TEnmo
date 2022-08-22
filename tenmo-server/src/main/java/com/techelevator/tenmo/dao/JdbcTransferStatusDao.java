package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TransferStatus getTransferStatusByDesc(String description) {
        String sql = "SELECT transfer_status_id, transfer_status_desc " +
                     "FROM transfer_statuses WHERE transfer_status_desc = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, description);
        TransferStatus transferStatus = null;
        if (result.next()) {
            long transferStatusID = result.getLong("transfer_status_id");
            String transferStatusDesc = result.getString("transfer_status_desc");
            transferStatus = new TransferStatus(transferStatusID, transferStatusDesc);
        }
        return transferStatus;
    }

    @Override
    public TransferStatus getTransferStatusById(long transferStatusId) {
        String sql = "SELECT transfer_status_id, transfer_status_desc " +
                     "FROM transfer_statuses WHERE transfer_status_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferStatusId);
        TransferStatus transferStatus = null;
        if(result.next()) {
            long transferStatusId2 = result.getLong("transfer_status_id");
            String transferStatusDesc = result.getString(("transfer_status_desc"));
            transferStatus = new TransferStatus(transferStatusId2, transferStatusDesc);
        }
        return transferStatus;
    }
}
