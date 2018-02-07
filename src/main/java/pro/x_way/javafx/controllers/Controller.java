package pro.x_way.javafx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pro.x_way.javafx.Listener;
import pro.x_way.javafx.Model.Message;
import pro.x_way.javafx.MySql;


import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class Controller {

    @FXML
    public TableColumn<Message, String> tabName;
    @FXML
    public TableColumn<Message, String> tabMessage;
    @FXML
    public TableView<Message> tabs;
    @FXML
    public TableColumn<Message, Date> tabDate;


    private static ObservableList<Message> list;
    private static BlockingQueue<Message> abq;


    @FXML
    private void initialize() {
        tabName.setCellValueFactory(new PropertyValueFactory<Message, String>("name"));
        tabMessage.setCellValueFactory(new PropertyValueFactory<Message, String>("message"));
        tabDate.setCellValueFactory(new PropertyValueFactory<Message, Date>("date"));
        prepareList();

        tabs.setItems(list);
    }


    private void prepareList() {
        abq = Listener.getInstance().getAbq();
        MySql.getMessage(null);

        MessageListener ms = new MessageListener();
        ms.setDaemon(true);
        ms.start();

        list = FXCollections.observableArrayList();
    }

    public void openAddTabs(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/addTab.fxml"));
            stage.setTitle("Создаь закладку");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private class MessageListener extends Thread {
        public void run() {
            try {
                while (true) {
                    Message message = abq.take();
                    if (message == null) {
                        break;
                    }
                    list.add(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
