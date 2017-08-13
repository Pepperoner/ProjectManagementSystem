package consoleView;

import api.API;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ConsoleHelper {

    private API api;
    private BufferedReader bufferReader;

    private final static Logger logger = Logger.getLogger(ConsoleHelper.class);

    public ConsoleHelper(API api, BufferedReader bufferReader) {
        this.api = api;
        this.bufferReader = bufferReader;
    }

    public void chooseMainOperations() {

        while (true) {

            System.out.println("*-------------------------------------------------*\n" +
                    "*-----> System for working with SQL database <-----*\n" +
                    "*-------------------------------------------------*");

            System.out.println("1. Menu selection table for viewing");
            System.out.println("2. Menu for adding data to database");
            System.out.println("3. Menu for editing data in database");
            System.out.println("4. Menu for deleting data in database");
            System.out.println("0. Exit");
            System.out.println("*-------------------------------------------*");

            System.out.println("\nEnter the number of the operation you want to perform!");
            try {
                switch (Integer.parseInt(bufferReader.readLine())) {
                    case 1:
                        System.out.println("*-------------------------------------------------*\n" +
                                "*------> Menu selection table for viewing <------*\n" +
                                "*-------------------------------------------------*");
                        readOperations();
                        break;
                    case 2:
                        System.out.println("\n*-----------------------------------------------------*\n" +
                                "*-----------> Menu for adding data to database <-----------*\n" +
                                "*-----------------------------------------------------*\n");
                        addOperations();
                        break;
                    case 3:
                        System.out.println("\n*-----------------------------------------------------*\n" +
                                "*-----------> Menu for editing data in database <-----------*\n" +
                                "*-----------------------------------------------------*\n");
                        updateOperations();
                        break;
                    case 4:
                        System.out.println("\n*-----------------------------------------------------*\n" +
                                "*-----------> Menu for deleting data in database <-----------*\n" +
                                "*-----------------------------------------------------*\n");
                        deleteOperations();
                        break;
                    case 0:
                        System.out.println("\n*------------------------------------------------------------------------------*\n" +
                                "*---------------> The program is complete, all changes are saved! <--------------*\n" +
                                "*------------------------------------------------------------------------------*\n");
                        return;
                    default:
                        System.out.println("Not correct operation number! Try again!" + " \n To exit, input \"0\"");
                }
            } catch (IOException e) {
                System.err.println("Data input / output error!");
                logger.error(e);
            } catch (NoSuchElementException | NumberFormatException n) {
                System.out.println("The command is entered incorrectly! Repeat the selection!" + " \n To exit, input \"0\"");
            }
        }
    }

    private void readOperations() {
        while (true) {
            System.out.println("1. Read all customer data");
            System.out.println("2. Read all company data");
            System.out.println("3. Read all project data");
            System.out.println("4. Read all developers data");
            System.out.println("5. Read all skill data");
            System.out.println("To return to the previous menu, input \"0\"!");

            System.out.println("\nEnter the number of the operation you want to perform:");

            try {
                int tableNumber = Integer.parseInt(bufferReader.readLine());
                if (tableNumber == 0) {
                    return;
                }
                api.readAllTable(tableNumber);
                System.out.println("To re-select tables to view, press 1, otherwise you will be taken to the main menu");
                String answer1 = bufferReader.readLine();
                switch (answer1) {
                    case "1":
                        continue;
                    default:
                        return;
                }
            } catch (IOException e) {
                System.err.println("Data input / output error!");
                logger.error(e);
            }

        }
    }

    private void addOperations() {
        while (true) {
            System.out.println("1. Add customer");
            System.out.println("2. Add company");
            System.out.println("3. Add project");
            System.out.println("4. Add developer");
            System.out.println("5. Add skill");
            System.out.println("To return to the previous menu, input \"0\"!");

            System.out.println("\nEnter the number of the operation you want to perform:");

            try {
                int tableNumber = Integer.parseInt(bufferReader.readLine());
                if (tableNumber == 0) {
                    return;
                }
                api.addOperations(tableNumber);
                System.out.println("To re-select the tables to add press 1, otherwise you will be taken to the main menu");
                String answer1 = bufferReader.readLine();
                switch (answer1) {
                    case "1":
                        continue;
                    default:
                        return;
                }
            } catch (IOException e) {
                System.err.println("Data input / output error!");
                logger.error(e);
            }
        }
    }

    private void updateOperations() {
        while (true) {
            System.out.println("1. Edit customer information");
            System.out.println("2. Edit company information");
            System.out.println("3. Edit project information");
            System.out.println("4. Edit developer information");
            System.out.println("5. Edit skill information");
            System.out.println("To return to the previous menu, input \"0\"!");

            System.out.println("\nEnter the number of the operation you want to perform:");

            try {
                int tableNumber = Integer.parseInt(bufferReader.readLine());
                if (tableNumber == 0) {
                    return;
                }
                api.updateOperations(tableNumber);
                System.out.println("To re-select the tables to edit press 1, otherwise you will be taken to the main menu");
                String answer1 = bufferReader.readLine();
                switch (answer1) {
                    case "1":
                        continue;
                    default:
                        return;
                }
            } catch (IOException e) {
                System.err.println("Data input / output error!");
                logger.error(e);
            }

        }
    }

    private void deleteOperations() {
        while (true) {
            System.out.println("1. Delete customer");
            System.out.println("2. Delete company");
            System.out.println("3. Delete project");
            System.out.println("4. Delete developer");
            System.out.println("5. Delete skill");
            System.out.println("To return to the previous menu, input \"0\"!");

            System.out.println("\nEnter the number of the operation you want to perform:");

            try {
                int tableNumber = Integer.parseInt(bufferReader.readLine());
                if (tableNumber == 0) {
                    return;
                }
                api.deleteOperations(tableNumber);
                System.out.println("To re-select the tables to delete press 1, otherwise you will be taken to the main menu");
                String answer1 = bufferReader.readLine();
                switch (answer1) {
                    case "1":
                        continue;
                    default:
                        return;
                }
            } catch (IOException e) {
                System.err.println("Data input / output error!");
                logger.error(e);
            }

        }
    }
}