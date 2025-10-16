package universite_paris8.iut.aclaudio.blackjack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BlackJack extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/aclaudio/blackjack/BlackJack.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        scene.getRoot().setStyle("-fx-background-color: #006400;");
        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}