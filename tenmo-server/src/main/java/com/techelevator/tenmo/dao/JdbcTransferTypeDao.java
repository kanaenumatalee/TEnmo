package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferTypeDao implements TransferTypeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TransferType getTransferTypeByDescription(String description) {
        String sql = "SELECT transfer_type_id, transfer_type_desc " +
                     "FROM transfer_type " +
                     "WHERE transfer_type_desc = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, description);
        TransferType transferType = null;

        if(result.next()) {
            int transferTypeId = result.getInt("transfer_type_id");
            String transferTypeDescription = result.getString("transfer_type_desc");
            transferType = new TransferType(transferTypeId, transferTypeDescription);
        }
        return transferType;
    }

    @Override
    public TransferType getTransferTypeById(int transferId) {
        String sql = "SELECT transfer_type_id, transfer_type_desc " +
                     "FROM transfer_types " +
                     "WHERE transfer_type_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

        TransferType transferType = null;

        if(result.next()) {
            int transferTypeId = result.getInt("transfer_type_id");
            String transferTypeDesc = result.getString("transfer_type_desc");
            transferType = new TransferType(transferTypeId, transferTypeDesc);
        }
        return transferType;
    }
}
