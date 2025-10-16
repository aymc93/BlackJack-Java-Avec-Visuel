module universite_paris8.iut.aclaudio.blackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens universite_paris8.iut.aclaudio.blackjack to javafx.fxml;
    exports universite_paris8.iut.aclaudio.blackjack;
}