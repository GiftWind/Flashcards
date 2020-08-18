package flashcards;

import java.util.*;

public class Main {

    public static final String ACTION_STRING = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DeckOfFlashcards deck = new DeckOfFlashcards();
        String output = "";
        int importIndex = Arrays.asList(args).indexOf("-import");
        int exportIndex = Arrays.asList(args).indexOf("-export");

        if (args.length > 0 && importIndex != -1) {
            deck.importFromFile(args[importIndex + 1]);
        }

        while (true) {
            System.out.println(ACTION_STRING);
            deck.addToLog(ACTION_STRING);
            String userAction = scanner.nextLine();
            deck.addToLog(userAction);
            switch (userAction) {
                case ("add"):
                    deck.addCardFromConsole();
                    break;
                case ("ask"):
                    output = "How many times to ask?";
                    System.out.println(output);
                    deck.addToLog(output);
                    String times = scanner.nextLine();
                    deck.addToLog(times);
                    int numbers = Integer.parseInt(times);
                    deck.askForSomeRandomCards(numbers);
                    break;
                case ("remove"):
                    output = "The card:";
                    System.out.println(output);
                    deck.addToLog(output);
                    String cardToRemove = scanner.nextLine();
                    deck.addToLog(cardToRemove);
                    deck.removeCard(cardToRemove);
                    break;
                case ("import"):
                    output = "File name:";
                    System.out.println(output);
                    deck.addToLog(output);
                    String fileToImportFrom = "";
                    fileToImportFrom = scanner.nextLine();
                    deck.addToLog(fileToImportFrom);
                    deck.importFromFile(fileToImportFrom);
                    break;
                case ("export"):
                    output = "File name:";
                    System.out.println(output);
                    deck.addToLog(output);
                    String fileToExportTo = "";
                    fileToExportTo = scanner.nextLine();
                    deck.addToLog(fileToExportTo);
                    deck.exportToFile(fileToExportTo);
                    break;
                case ("hardest card"):
                    deck.printHardestCard();
                    break;
                case ("reset stats"):
                    deck.resetStats();
                    break;

                case ("log"):
                    output = "File name:";
                    System.out.println(output);
                    deck.addToLog(output);
                    String fileName = scanner.nextLine();
                    deck.addToLog(fileName);
                    deck.saveToLogFIle(fileName);
                    System.out.println("The log has been saved.");
                    break;
            }
            if (userAction.equals("exit")) {
                System.out.println("Bye bye!");
                if (args.length > 0 && exportIndex != -1) {
                    deck.exportToFile(args[exportIndex + 1]);
                }
                break;
            }
        }
    }
}
