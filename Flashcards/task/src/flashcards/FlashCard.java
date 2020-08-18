package flashcards;

public class FlashCard {
    private String card;
    private String definition;

    public FlashCard(String card, String definition) {
        this.card = card;
        this.definition = definition;
    }

    public String getCard() {
        return card;
    }
    public String getDefinition() {
        return definition;
    }
}
