package universite_paris8.iut.aclaudio.blackjack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.Collections;

public class BlackJackController {
    @FXML private HBox playerHandBox;
    @FXML private HBox dealerHandBox;
    @FXML private Label playerValue;
    @FXML private Label dealerValue;
    @FXML private Label resultLabel;
    @FXML private Button hitButton;
    @FXML private Button standButton;
    @FXML private Button restartButton;

    private ArrayList<String> deck = new ArrayList<>();
    private ArrayList<String> playerHand = new ArrayList<>();
    private ArrayList<String> dealerHand = new ArrayList<>();
    private boolean gameEnded = false;

    @FXML
    public void initialize() {
        deck = listCards();
        Collections.shuffle(deck);
        baseAction();
        updateUI();
    }

    public static int getHandValue(ArrayList<String> hand) {
        int total = 0;
        int nombreAs = 0;

        for (String card : hand) {
            String valeur = card.substring(2).trim();
            if (valeur.equals("As")) {
                nombreAs++;
                total += 11;
            } else if (valeur.equals("Valet") || valeur.equals("Reine") || valeur.equals("Roi")) {
                total += 10;
            } else {
                total += Integer.parseInt(valeur);
            }
        }

        while (total > 21 && nombreAs > 0) {
            total -= 10;
            nombreAs--;
        }
        return total;
    }

    public static ArrayList<String> listCards() {
        ArrayList<String> cards = new ArrayList<>();
        String[] couleurs = {"♠", "♣", "♥", "♦"};
        String[] valeurs = {" As", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", " 10", " Valet", " Reine", " Roi"};
        for (String val : valeurs) {
            for (String coul : couleurs) {
                cards.add(coul + val);
            }
        }
        return cards;
    }

    private void baseAction() {
        playerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
    }

    @FXML
    private void hitAction() {
        if (getHandValue(playerHand) <= 21) {
            playerHand.add(deck.remove(0));
            updateUI();
            if (getHandValue(playerHand) > 21) {
                gameEnded = true;
                resultLabel.setText("💥 Vous avez dépassé 21 ! Vous perdez.");
                updateUI();
                disableButtons();
            }
        }
    }

    @FXML
    private void standAction() {
        dealerTurn();
        gameEnded = true;
        updateUI();
        determineWinner();
    }

    @FXML
    private void restartAction() {
        deck = listCards();
        Collections.shuffle(deck);
        playerHand.clear();
        dealerHand.clear();
        gameEnded = false;
        resultLabel.setText("");
        hitButton.setDisable(false);
        standButton.setDisable(false);
        baseAction();
        updateUI();
    }

    private void dealerTurn() {
        int dealerScore = getHandValue(dealerHand);
        int playerScore = getHandValue(playerHand);
        if (playerScore <= 21) {
            while (dealerScore < 17) {
                dealerHand.add(deck.remove(0));
                dealerScore = getHandValue(dealerHand);
            }
        }
    }

    private void determineWinner() {
        int playerScore = getHandValue(playerHand);
        int dealerScore = getHandValue(dealerHand);

        if (playerScore > 21) {
            resultLabel.setText("Vous avez perdu !");
        } else if (dealerScore > 21 || playerScore > dealerScore) {
            resultLabel.setText("🎉 Vous gagnez !");
        } else if (dealerScore > playerScore) {
            resultLabel.setText("Le Dealer gagne !");
        } else {
            resultLabel.setText("Égalité !");
        }
        disableButtons();
    }

    private void updateUI() {
        playerHandBox.getChildren().clear();
        dealerHandBox.getChildren().clear();

        for (String card : playerHand) {
            Label cardLabel = new Label(card);
            cardLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.5); -fx-padding: 5;");
            playerHandBox.getChildren().add(cardLabel);
        }

        for (String card : dealerHand) {
            Label cardLabel;
            if (!gameEnded && dealerHand.indexOf(card) != 0) {
                cardLabel = new Label("🂠");
            } else {
                cardLabel = new Label(card);
            }
            cardLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.5); -fx-padding: 5;");
            dealerHandBox.getChildren().add(cardLabel);
        }

        playerValue.setText("Valeur : " + getHandValue(playerHand));
        dealerValue.setText("Valeur : " + (gameEnded ? getHandValue(dealerHand) : "?"));
    }

    private void disableButtons() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
    }
}