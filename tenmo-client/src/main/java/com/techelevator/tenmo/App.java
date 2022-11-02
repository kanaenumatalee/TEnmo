package com.techelevator.tenmo;


import com.techelevator.exception.InvalidTransferIdException;
import com.techelevator.exception.NegativeValueException;
import com.techelevator.exception.NoUserFoundException;
import com.techelevator.exception.NotEnoughBalanceException;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import org.apache.commons.lang3.StringUtils;

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

    
    public static void main(String[] args) throws NegativeValueException {
        App app = new App();
        app.run();
    }

    private void run() throws NegativeValueException {
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
            System.out.println();
            System.out.println("Registration successful. You can now login.");
        } else {
            System.out.println();
            consoleService.printErrorMessage();
        }
        System.out.println();
        consoleService.printGreeting();
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            System.out.println();
            consoleService.printErrorMessage();
            consoleService.printGreeting();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            System.out.println(StringUtils.center("", 50, "-"));
            System.out.println("|" + StringUtils.center("", 48, " ") + "|");
            System.out.println("|" + StringUtils.center("You are logged in as: " + currentUser.getUser().getUsername(), 48) + "|");
            System.out.println("|" + StringUtils.center("", 48, " ") + "|");
            System.out.println(StringUtils.center("", 50, "-"));
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
            } else if (menuSelection == 6) {
                handleLogin();
            } else if (menuSelection == 0) {
                consoleService.printForExiting();
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        // print current balance
        System.out.println("Your current balance: $" + accountService.getBalance(currentUser));
    }

    private void sendBucks() {
        //TODO print user list
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("User List", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("| [UserID]                            [Username] |");
        System.out.println(StringUtils.center("", 50, "-"));
        User[] users = userService.getAllUsers(currentUser);
        consoleService.printUsers(users, currentUser);
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println();
        int userIdInput = consoleService.promptForInt("Please enter UserID you would like to send to (0 to cancel): ");
        // validate userId and input value amount
        if(isValidUserId(userIdInput, users)) {
            int userAmountInput = consoleService.promptForInt("Please enter amount to send: ");
            BigDecimal balance = accountService.getBalance(currentUser);
            BigDecimal userAmount = BigDecimal.valueOf(userAmountInput);
            try {
                if (userAmountInput <= 0) {
                    throw new NegativeValueException();
                } else if (userAmount.compareTo(balance) > 0) {
                    throw new NotEnoughBalanceException();
                } else {
                    makeTransfer(userIdInput, "Send", "Approved", BigDecimal.valueOf(userAmountInput));
                }
            } catch (NegativeValueException | NotEnoughBalanceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isValidUserId(long userId, User[] users) {
        boolean isValidId = false;
        if(userId != 0) {
            try {
                for(User user : users) {
                    if(userId == user.getId()) {
                        isValidId = true;
                        break;
                    }
                }
                if (!isValidId){
                    throw new NoUserFoundException();
                }
               return true;
            } catch(NoUserFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    private Transfer makeTransfer(int userAccountChoice, String transferType, String statusDescription, BigDecimal amount) {
        Transfer transfer = new Transfer();
        int transferTypeId = transferTypeService.getTransferTypeByDescription(currentUser, transferType).getTransferTypeId();
        int transferStatusId = transferStatusService.getTransferStatus(currentUser, statusDescription).getTransferStatusId();
        int accountToId;
        int accountFromId;
        String transactionStatus = "";
        // Assign accountToId/accountFromId
        if(transferType.equals("Send")) {
            accountToId = accountService.getAccountByUserId(currentUser, userAccountChoice).getAccountId();
            accountFromId = accountService.getAccountByUserId(currentUser, Math.toIntExact(currentUser.getUser().getId())).getAccountId();
            transactionStatus = "Transaction complete!";
        } else {
            // Request
            accountToId = accountService.getAccountByUserId(currentUser, Math.toIntExact(currentUser.getUser().getId())).getAccountId();
            accountFromId = accountService.getAccountByUserId(currentUser, userAccountChoice).getAccountId();
            transactionStatus = "Request complete!";
        }
        // set values to Transfer object
        if (accountFromId != accountToId) {
            transfer.setAccountFrom(accountFromId);
            transfer.setAccountTo(accountToId);
            transfer.setAmount(amount);
            transfer.setTransferStatusId(transferStatusId);
            transfer.setTransferTypeId(transferTypeId);
            transferService.makeTransfer(currentUser, transfer);
        } else {
            transactionStatus = "You cannot make a transaction to yourself.\nTransaction cancelled.";
        }
        System.out.println(transactionStatus);
        return transfer;
    }

    private void viewTransferHistory() {
        // print transfer history
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("Transfer History", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("| [TransferID]      [From/To]           [Amount] |");
        System.out.println(StringUtils.center("", 50, "-"));
        Transfer[] transfers = transferService.viewTransferHistory(currentUser);
        int accFromUserId = 0;
        int accToUserId = 0;
        for(Transfer transfer: transfers) {
            accFromUserId = accountService.getAccountByAccountId(currentUser, transfer.getAccountFrom()).getUserId();
            accToUserId = accountService.getAccountByAccountId(currentUser, transfer.getAccountTo()).getUserId();
            if (accFromUserId == currentUser.getUser().getId() && transfer.getTransferStatusId() == 2 ||
                    accToUserId == currentUser.getUser().getId() && transfer.getTransferStatusId() == 2) {
                printTransfers(currentUser, transfer);
            }
        }
        System.out.println(StringUtils.center("", 50, "-"));
        int transferIdInput = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");
        if (transferIdInput == 0) {
        } else {
            Transfer transfer = validateTransferId(transferIdInput, transfers, currentUser);
            if (transfer != null &&
                    accFromUserId == currentUser.getUser().getId() && transfer.getTransferStatusId() == 2 ||
                    accToUserId == currentUser.getUser().getId() && transfer.getTransferStatusId() == 2) {
                printTransferDetails(currentUser, transfer);
            } else {
                System.out.println("Invalid transfer ID.");
            }
        }
    }

    private Transfer validateTransferId(long transferId, Transfer[] transfers, AuthenticatedUser authenticatedUser) {
        Transfer transferOption = null;
        boolean validTransferId = false;
        if (transferId != 0) {
            try {
                for (Transfer transfer : transfers) {
                    if (transfer.getTransferId() == transferId) {
                        transferOption = transfer;
                        validTransferId = true;
                        break;
                    }
                }
                if (!validTransferId) {
                    throw new InvalidTransferIdException();
                }
            } catch (InvalidTransferIdException e) {
                System.out.println(e.getMessage());
            }
        }
        return transferOption;
    }

    //As an authenticated user of the system, I need to be able to retrieve the details of any transfer based upon the transfer ID.
    private void printTransferDetails(AuthenticatedUser authenticatedUser, Transfer transfer) {
        int transferId = transfer.getTransferId();
        BigDecimal money = transfer.getAmount();
        int accountFrom = transfer.getAccountFrom();
        int accountTo = transfer.getAccountTo();
        int transferTypeId = transfer.getTransferTypeId();
        int transferStatusId = transfer.getTransferStatusId();
        int fromUserId = accountService.getAccountByAccountId(authenticatedUser, accountFrom).getUserId();
        String fromUserName = userService.getUserByUserId(authenticatedUser, fromUserId).getUsername();
        int toUserId = accountService.getAccountByAccountId(authenticatedUser, accountTo).getUserId();
        String toUserName = userService.getUserByUserId(authenticatedUser, toUserId).getUsername();
        String transferType = transferTypeService.getTransferTypeById(authenticatedUser, transferTypeId).getTransferTypeDescription();
        String transferStatus = transferStatusService.getTransferStatusById(authenticatedUser, transferStatusId).getTransferStatusDesc();
        consoleService.printTransferDetails(transferId, fromUserName, toUserName, transferType, transferStatus, money);
    }

    private void requestBucks() {
        // print user list
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("User List", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("| [UserID]                            [Username] |");
        System.out.println(StringUtils.center("", 50, "-"));
        User[] users = userService.getAllUsers(currentUser);
        consoleService.printUsers(users, currentUser);
        System.out.println(StringUtils.center("", 50, "-"));
        int userIdInput = consoleService.promptForInt("Please enter user ID you would like to request from (0 to cancel): ");
        if(isValidUserId(userIdInput, users)) {
            int userAmountInput = consoleService.promptForInt("Please enter amount to request: ");
            try {
                if (userAmountInput <= 0) {
                    throw new NegativeValueException();
                } else {
                    makeTransfer(userIdInput, "Request", "Pending", BigDecimal.valueOf(userAmountInput));
                }
            } catch (NegativeValueException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void viewPendingRequests() {
        // print pending transfer
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("Pending Requests", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("| [TransferID]       [To]               [Amount] |");
        System.out.println(StringUtils.center("", 50, "-"));
        Transfer[] transfers = transferService.getPendingTransfersByUserId(currentUser);
        if (transfers.length != 0) {
            for (Transfer transfer : transfers) {
                printTransfers(currentUser, transfer);
            }
            System.out.println(StringUtils.center("", 50, "-"));
            System.out.println();
            // approve or reject
            int transferOption = consoleService.promptForInt("Please enter transfer ID to approve/reject (0 to cancel) : ");
            Transfer transfer = validateTransferId(transferOption, transfers, currentUser);
            if(transfer != null) {
                approveOrReject(transfer, currentUser);
            }
        } else {
            System.out.println();
            System.out.println("No pending transfer to show.");
        }
    }

    //As an authenticated user of the system, I need to be able to see transfers I have sent or received.
    private void printTransfers(AuthenticatedUser authenticatedUser, Transfer transfer) {
        String transferFromOrTo = "";
        int accountFrom = transfer.getAccountFrom();
        int accountTo = transfer.getAccountTo();
        // if authUser's accountfrom's ID == authUser's ID
        if (accountService.getAccountByAccountId(authenticatedUser, accountTo).getUserId() == authenticatedUser.getUser().getId()) {
            // get accountFrom ID and use it to get accountFrom username
            int accountFromId = accountService.getAccountByAccountId(authenticatedUser, accountFrom).getUserId();
            String userFrom = userService.getUserByUserId(authenticatedUser, accountFromId).getUsername();
            transferFromOrTo = "From: " + userFrom;
        } else {
            // get accountTo ID and use it to get accountTo username
            int accountToId = accountService.getAccountByAccountId(authenticatedUser, accountTo).getUserId();
            String userTo = userService.getUserByUserId(authenticatedUser, accountToId).getUsername();
            transferFromOrTo = "To: " + userTo;
        }
        consoleService.printTransfers(transfer.getTransferId(), transferFromOrTo, transfer.getAmount());
    }

    /*
    As an authenticated user of the system, I need to be able to either approve or reject a Request Transfer.
    I can't "approve" a given Request Transfer for more TE Bucks than I have in my account.
    The Request Transfer status is Approved if I approve, or Rejected if I reject the request.
    If the transfer is approved, the requester's account balance is increased by the amount of the request.
    If the transfer is approved, the requestee's account balance is decreased by the amount of the request.
    If the transfer is rejected, no account balance changes.
    */
    private void approveOrReject(Transfer transfer, AuthenticatedUser authenticatedUser) {
        consoleService.printApproveOrRejectChoices();
        int option = consoleService.promptForInt("Please choose an option: ");
        int transferStatusId;
        if(option != 0) {
            if(option == 1) {
                transferStatusId = transferStatusService.getTransferStatus(authenticatedUser, "Approved").getTransferStatusId();
                transfer.setTransferStatusId(transferStatusId);
                BigDecimal balance = accountService.getBalance(currentUser);
                BigDecimal amount = transfer.getAmount();
                try {
                    if (amount.compareTo(balance) >= 0) {
                       throw new NotEnoughBalanceException();
                    } else {
                        System.out.println("You approved the request. Transaction is complete!");
                    }
                } catch (NotEnoughBalanceException e){
                        System.out.println(e.getMessage());
                    }
            } else if (option == 2) {
                transferStatusId = transferStatusService.getTransferStatus(authenticatedUser, "Rejected").getTransferStatusId();
                transfer.setTransferStatusId(transferStatusId);
                System.out.println("You rejected the request.");
            } else {
                System.out.println("Invalid option.");
            }
            transferService.updateTransferStatus(authenticatedUser, transfer);
        }
    }
}
