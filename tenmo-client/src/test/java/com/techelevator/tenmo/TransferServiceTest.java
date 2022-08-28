package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class TransferServiceTest {

    private static final String EXPECTED_API_URL = "http://localhost:8080/";

    private final RestTemplate restTemplate = new RestTemplate();
    private final TransferService sut = new TransferService(EXPECTED_API_URL);

    private final AccountService accountService = new AccountService(EXPECTED_API_URL);
    private final UserService userService = new UserService(EXPECTED_API_URL);
    private final TransferService transferService = new TransferService(EXPECTED_API_URL);
    private static final int TEST_ID = 1001;


    @Test
    public void verify_view_transfer_history(){
        ReflectionTestUtils.setField(sut, "restTemplate", restTemplate);
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);

        server.expect(requestTo(EXPECTED_API_URL + "/users/transfer/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":" + TEST_ID + "}", MediaType.APPLICATION_JSON));

        Transfer transfer = null;

        try{

            //transfer = sut.viewTransferHistory(authenticatedUser);

        }
        catch (AssertionError e) {
            fail("Didn't send the expected request to retrieve past transfers. Verify that the URL, HTTP method, and headers are correct.");
        }
        Assert.assertNotNull("Call to viewTransferHistory() returned null.", transfer);
        Assert.assertEquals("Call to viewTransferHistory() didn't return the expected data.", TEST_ID, transfer.getTransferId());
    }

    @Test
    public void verify_get_all_transfers(){
        ReflectionTestUtils.setField(sut, "restTemplate", restTemplate);
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);

        server.expect(requestTo(EXPECTED_API_URL + "/users/transfer/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":" + TEST_ID + "}", MediaType.APPLICATION_JSON));

        Transfer transfer = null;

        try{

            //transfer = sut.getAllTransfers(/authenticatedUser*/);

        }
        catch (AssertionError e) {
            fail("Didn't send the expected request to retrieve all transfers. Verify that the URL, HTTP method, and headers are correct.");
        }
        Assert.assertNotNull("Call to viewTransferHistory() returned null.", transfer);
        Assert.assertEquals("Call to viewTransferHistory() didn't return the expected data.", TEST_ID, transfer.getTransferId());
    }

    @Test
    public void verify_update_transfer_status(){
        ReflectionTestUtils.setField(sut, "restTemplate", restTemplate);
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);

        Transfer newTransfer = new Transfer(1000, 1, 1, 101, 102, BigDecimal.valueOf(500));
        Transfer expectedTransfer = new Transfer(1000, 1, 2, 101, 102, BigDecimal.valueOf(500));

        when(restTemplate.postForObject(
                Mockito.eq(EXPECTED_API_URL + "/users/transfer/"),
                Mockito.eq(makeEntityHelper(newTransfer)),
                Mockito.eq(Transfer.class))).thenReturn(expectedTransfer);

        Transfer actualTransfer = null;

        //actualTransfer = sut.updateTransferStatus(/**authenticatedUser*/);

        Assert.assertEquals("auctionService.add() should call the API and return the newly created auction", expectedTransfer, actualTransfer);

    }


    private HttpEntity<Transfer> makeEntityHelper(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transfer, headers);
    }

}