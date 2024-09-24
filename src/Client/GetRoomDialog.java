package Client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GetRoomDialog {
    @FXML
    TextField nameField;

    @FXML
    Button goButton;

    Stage stage;
    String roomname;

    public GetRoomDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("getNameUI.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Battle Joker");
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());

        goButton.setOnMouseClicked(this::OnButtonClick);

        stage.showAndWait();
    }

    @FXML
    void OnButtonClick(Event event) {
        roomname = nameField.getText().trim();
        if (roomname.length() > 0)
            stage.close();
    }

    public String getRoomname() {
        return roomname;
    }
}
