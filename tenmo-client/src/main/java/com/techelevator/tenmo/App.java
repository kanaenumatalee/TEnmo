package com.techelevator.tenmo;

import com.techelevator.exception.InvalidUserException;
import com.techelevator.exception.NoUserFoundException;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final UserService userService = new UserService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);
    private final TransferTypeService transferTypeService = new TransferTypeService(API_BASE_URL);
    private final TransferStatusService transferStatusService = new TransferStatusService(API_BASE_URL);




    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    // As an authenticated user of the system, I need to be able to see my Account Balance.
    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        System.out.println("Your current balance: $" + accountService.getBalance(currentUser));
    }


    /*
    As an authenticated user of the system, I need to be able to send a transfer of a specific amount of TE Bucks to a registered user.
    I should be able to choose from a list of users to send TE Bucks to.
    I must not be allowed to send money to myself.
    A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
    The receiver's account balance is increased by the amount of the transfer.
    The sender's account balance is decreased by the amount of the transfer.
    I can't send more TE Bucks than I have in my account.
    I can't send a zero or negative amount.
    A Sending Transfer has an initial status of Approved.
    */
    private void sendBucks() {
        // TODO Auto-generated method stub
        System.out.println("----Here is your user list----");
        System.out.println("    [UserID]    [Username]");
        User[] users = userService.getAllUsers(currentUser);
        consoleService.printUsers(users);
        int userIdInput = consoleService.promptForInt("Please enter UserID you would like to send to: ");
        if(isValidUserId(userIdInput, users, currentUser)) {
            int userAmountInput = consoleService.promptForInt("Please enter amount to send: ");
            makeTransfer(userIdInput, "Send", "Approved", BigDecimal.valueOf(userAmountInput));
        }

    }

    private boolean isValidUserId(long userId, User[] users, AuthenticatedUser authenticatedUser) {
        boolean isValidId = false;
        if(userId != 0) {
            try {
                for(User user : users) {
                    if (userId == authenticatedUser.getUser().getId()) {   //user choose themselves = error
                        throw new InvalidUserException();
                    } else if(user.getId() == userId) {
                        isValidId = true;
                        break;
                    } else {
                        throw new NoUserFoundException();
                    }
                }
            } catch(InvalidUserException | NoUserFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return isValidId;
    }


    private Transfer makeTransfer(long accountTo, String transferType, String statusDescription, BigDecimal amount) {
        Transfer transfer = new Transfer();
        long transferTypeId = transferTypeService.getTransferType(currentUser, transferType).getTransferTypeId();
        long transferStatusId = transferStatusService.getTransferStatus(currentUser, statusDescription).getTransferStatusId();
        long accountToId;
        long accountFromId;
        if(transferType.equals("Send")) {
            accountToId = accountService.getAccountByUserId(currentUser, accountTo).getAccountId();
            accountFromId = accountService.getAccountByUserId(currentUser, currentUser.getUser().getId()).getAccountId();
        } else {
            accountFromId = accountService.getAccountByUserId(currentUser, accountTo).getAccountId();
            accountToId = accountService.getAccountByUserId(currentUser, currentUser.getUser().getId()).getAccountId();
        }

        transfer.setAccountFrom(accountFromId);
        transfer.setAccountTo(accountToId);
        transfer.setAmount(amount);
        transfer.setTransferStatusId(transferStatusId);
        transfer.setTransferTypeId(transferTypeId);


        transferService.makeTransfer(currentUser, transfer);
        return transfer;
    }



    //As an authenticated user of the system, I need to be able to see transfers I have sent or received.
    private void viewTransferHistory() {
        // TODO Auto-generated method stub
        System.out.println("-----View transfer history------");
        Transfer[] transfers = transferService.viewTransferHistory(currentUser);
        for(Transfer transfer: transfers) {
            System.out.println(transfer);
        }

    }


    /*
    As an authenticated user of the system, I need to be able to retrieve the details of any transfer based upon the transfer ID.
    */







    /*
    As an authenticated user of the system, I need to be able to request a transfer of a specific amount of TE Bucks from another registered user.
    I should be able to choose from a list of users to request TE Bucks from.
    I must not be allowed to request money from myself.
    I can't request a zero or negative amount.
    A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
    A Request Transfer has an initial status of Pending.
    No account balance changes until the request is approved.
    The transfer request should appear in both users' list of transfers (use case #5).
    */
    private void requestBucks() {
        // TODO Auto-generated method stub
        System.out.println("----Here is your user list----");
        System.out.println("    [UserID]    [Username]");
        User[] users = userService.getAllUsers(currentUser);
        consoleService.printUsers(users);
        int userIdInput = consoleService.promptForInt("Please enter UserID you would like to request from: ");
        if(isValidUserId(userIdInput, users, currentUser)) {
            int userAmountInput = consoleService.promptForInt("Please enter amount to request: ");
            makeTransfer(userIdInput, "Request", "Pending", BigDecimal.valueOf(userAmountInput));
        }

    }



    //As an authenticated user of the system, I need to be able to see my Pending transfers.
    private void viewPendingRequests() {
        // TODO Auto-generated method stub
        System.out.println("-----View pending transfers------");
        Transfer[] transfers = transferService.getPendingTransfersByUserId(currentUser);
        for(Transfer transfer: transfers) {
            System.out.println(transfer);
        }

    }





    /*
    As an authenticated user of the system, I need to be able to either approve or reject a Request Transfer.
    I can't "approve" a given Request Transfer for more TE Bucks than I have in my account.
    The Request Transfer status is Approved if I approve, or Rejected if I reject the request.
    If the transfer is approved, the requester's account balance is increased by the amount of the request.
    If the transfer is approved, the requestee's account balance is decreased by the amount of the request.
    If the transfer is rejected, no account balance changes.
    */

}
