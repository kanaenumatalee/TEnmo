package com.techelevator.tenmo.services;


import com.techelevator.exception.NegativeValueException;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);


    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("**************************************************");
        System.out.println("*                                                *");
        System.out.println("*               Welcome to TEnmo!                *");
        System.out.println("*                                                *");
        System.out.println("**************************************************");
    }

    public void printForExiting() {
        System.out.println();
        System.out.println("**************************************************");
        System.out.println("*                                                *");
        System.out.println("*          Thank you for using TEnmo!            *");
        System.out.println("*                                                *");
        System.out.println("**************************************************");
    }

    public void printLoginMenu() {
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.printf("%-2s%-47s%1s","| ","1: Register","|\n");
        System.out.printf("%-2s%-47s%1s","| ","2: Login","|\n");
        System.out.printf("%-2s%-47s%1s","| ","0: Exit","|\n");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.printf("%-2s%-47s%1s","| ","1: View your current balance","|\n");
        System.out.printf("%-2s%-47s%1s","| ","2: View your past transfers","|\n");
        System.out.printf("%-2s%-47s%1s","| ","3: View your pending requests","|\n");
        System.out.printf("%-2s%-47s%1s","| ","4: Send TE bucks","|\n");
        System.out.printf("%-2s%-47s%1s","| ","5: Request TE bucks","|\n");
        System.out.printf("%-2s%-47s%1s","| ","6: Log in as different user","|\n");
        System.out.printf("%-2s%-47s%1s","| ","0: Exit","|\n");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }


    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }


    public void printUsers(User[] users, AuthenticatedUser authenticatedUser) {
        for(User user: users) {
            if(!user.getUsername().equals(authenticatedUser.getUser().getUsername())) {
                System.out.printf("| %-23s%23s |",user.getId(), user.getUsername());
                System.out.println();
            }
        }
        System.out.flush();
    }

    public void printTransfers(int transferId, String transferFromOrTo, BigDecimal money) {
        System.out.printf("| %-19s%-12s%15s |", transferId, transferFromOrTo, "$" + money);
        System.out.println();
    }

    public void printTransferDetails(int transferId, String from, String to, String type, String status, BigDecimal money) {
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("Transfer Details", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.printf("%-2s%-47s%1s","| ","Id: " + transferId,"|\n");
        System.out.printf("%-2s%-47s%1s","| ","From: " + from,"|\n");
        System.out.printf("%-2s%-47s%1s","| ","To: " + to,"|\n");
        System.out.printf("%-2s%-47s%1s","| ","Type: " + type,"|\n");
        System.out.printf("%-2s%-47s%1s","| ","Status: " + status,"|\n");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println();
    }


    public void printApproveOrRejectChoices() {
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("Pending Choices", 48, " ") + "|");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.printf("%-2s%-47s%1s","| ","1: Approve","|\n");
        System.out.printf("%-2s%-47s%1s","| ","2: Reject","|\n");
        System.out.printf("%-2s%-47s%1s","| ","0: Don't approve or reject","|\n");
        System.out.println("|" + StringUtils.center("", 48, " ") + "|");
        System.out.println(StringUtils.center("", 50, "-"));
        System.out.println();
    }

}
