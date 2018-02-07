package pro.x_way.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pro.x_way.javafx.MySql;

public class AddMessage {
    @FXML
    public TextField messageName;
    @FXML
    public TextArea messageFullText;

    public void addTab(ActionEvent actionEvent) {
        if (!messageName.getText().equals("") & !messageName.getText().equals("")) {
            MySql.addMessage(messageName.getText(), messageFullText.getText());
        }

        actionClose(actionEvent);
    }

    public void actionClose(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
