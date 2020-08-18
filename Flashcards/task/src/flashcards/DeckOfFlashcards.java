package flashcards;

import java.io.*;
import java.util.*;

public class DeckOfFlashcards {
    Map<FlashCard, Integer> deck = new HashMap<>();
    ArrayList<String> log = new ArrayList<>();
    String output = "";

    public void addCardFromConsole() {
        Scanner scanner2 = new Scanner(System.in);
        String card = "";
        String definition = "";

        output = "The card:";
        System.out.println(output);
        addToLog(output);

        card = scanner2.nextLine();
        addToLog(card);
        if (containsCard(card)) {
            output = String.format("The card \"%s\" already exists.", card);
            System.out.println(output);
            addToLog(output);
            return;
        } else {
            output = "The definition of the card:";
            System.out.println(output);
            addToLog(output);
            definition = scanner2.nextLine();
            addToLog(definition);
            if (containsDefinition(definition)) {
                output = String.format("The definition \"%s\" already exists.", definition);
                System.out.println(output);
                addToLog(output);
            } else {
                deck.put(new FlashCard(card, definition), 0);
                output = String.format("The pair (\"%s\":\"%s\") has been added.", card, definition);
                System.out.println(output);
                addToLog(output);
            }
        }
    }

    private boolean containsDefinition(String definition) {
        for (FlashCard flashCard: deck.keySet()) {
            if (definition.equals(flashCard.getDefinition())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsCard(String card) {
        for (FlashCard flashCard: deck.keySet()) {
            if (card.equals(flashCard.getCard())) {
                return true;
            }
        }
        return false;
    }

    public void addToLog(String s) {
        log.add(s);
    }

    public void askForRandomCard() {
        List<FlashCard> cardsList = new ArrayList<>(deck.keySet());
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int randomIndex = random.nextInt(cardsList.size());
        FlashCard randomCard = cardsList.get(randomIndex);
        output = String.format("Print the definition of \"%s\":", randomCard.getCard());
        System.out.println(output);
        addToLog(output);
            String answer = scanner.nextLine();
            addToLog(answer);
            if (answer.equals(randomCard.getDefinition())) {
                output = "Correct!";
                System.out.println(output);
                addToLog(output);

            } else if (containsDefinition(answer)) {
                deck.replace(randomCard, deck.get(randomCard) + 1);
                output = String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".", randomCard.getDefinition(), getCardByDefinition(answer));
                System.out.println(output);
                addToLog(output);

            } else {
                deck.replace(randomCard, deck.get(randomCard) + 1);
                output = String.format("Wrong. The right answer is \"%s\".", randomCard.getDefinition());
                System.out.println(output);
                addToLog(output);
            }

    }

    private String getCardByDefinition(String definition) {
        for (FlashCard flashCard: deck.keySet()) {
            if (definition.equals(flashCard.getDefinition())) {
                return flashCard.getCard();
            }
        }
        return "Not found";
    }

    public void askForSomeRandomCards(int numbers) {
        for (int i = 0; i < numbers; i++) {
            askForRandomCard();
        }
    }

    public void removeCard(String cardToRemove) {
        for (FlashCard flashCard: deck.keySet()) {
            if (cardToRemove.equals(flashCard.getCard())) {
                deck.remove(flashCard);
                output = "The card has been removed.";
                System.out.println(output);
                addToLog(output);
                return;
            }
        }
        output = String.format("Can't remove %s: there is no such card.", cardToRemove);
        System.out.println(output);
        addToLog(output);
    }

    public void printHardestCard() {
        int maxMistakes = 0;
        for (FlashCard flashCard: deck.keySet()) {
            if (deck.get(flashCard) > maxMistakes) {
                maxMistakes = deck.get(flashCard);
            }
        }
        ArrayList<FlashCard> hardestCards = new ArrayList<>();
        for (FlashCard flashCard: deck.keySet()) {
            if (deck.get(flashCard) == maxMistakes && deck.get(flashCard) != 0) {
                hardestCards.add(flashCard);
            }
        }
        switch (hardestCards.size()) {
            case (0):
                output = "There are no cards with errors.";
                System.out.println(output);
                addToLog(output);
                break;
            case (1):
                output = String.format("The hardest card is \"%s\". You have %d errors answering it.", hardestCards.get(0).getCard(), deck.get(hardestCards.get(0)));
                System.out.println(output);
                addToLog(output);
                break;
            default:
                StringBuilder hardestCardsString = new StringBuilder();
                for (FlashCard flashCard: hardestCards) {
                    hardestCardsString.append("\"");
                    hardestCardsString.append(flashCard.getCard());
                    hardestCardsString.append("\"");
                    hardestCardsString.append(", ");
                }
                hardestCardsString.deleteCharAt(hardestCardsString.length() - 1);
                hardestCardsString.deleteCharAt(hardestCardsString.length() - 1);
                output = String.format("The hardest cards are %s. You have %d errors answering them.", hardestCardsString, maxMistakes);
                System.out.println(output);
                addToLog(output);
        }
    }

    public void saveToLogFIle(String fileName) {
        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String s: log) {
                printWriter.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetStats() {
        for (FlashCard flashCard: deck.keySet()) {
            deck.replace(flashCard, 0);
        }
        output = "Card statistics has been reset";
        System.out.println(output);
        addToLog(output);
    }

    public void exportToFile(String fileToExportTo) {
        File file = new File(fileToExportTo);
        String entry = "";
        int counter = 0;
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (FlashCard flashCard: deck.keySet()) {
                entry = flashCard.getCard() + ":" + flashCard.getDefinition() + ":" + deck.get(flashCard);
                printWriter.println(entry);
                counter++;
            }
            output = String.format("%d cards have been saved.", counter);
            System.out.println(output);
            addToLog(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void importFromFile(String fileToImportFrom) {
        File file = new File(fileToImportFrom);
        String card, definition, mistakes;
        int counter = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNext()) {
                String[] entry = fileScanner.nextLine().split(":");
                card = entry[0];
                definition = entry[1];
                mistakes = entry[2];
                for (FlashCard flashCard: deck.keySet()) {
                    if (card.equals(flashCard.getCard())) {
                        deck.remove(flashCard);
                        break;
                    }
                }
                FlashCard flashCardToAdd = new FlashCard(card, definition);
                deck.put(flashCardToAdd, Integer.parseInt(mistakes));
                counter++;
            }
            output = String.format("%d cards have been loaded.", counter);
            System.out.println(output);
            addToLog(output);
        } catch (FileNotFoundException e) {
            output = "File not found.";
            System.out.println(output);
            addToLog(output);
        }
    }
}
