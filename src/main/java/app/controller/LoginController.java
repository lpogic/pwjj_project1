package app.controller;

import app.core.pane.OpenController;
import app.dealer.HooktheoryDealer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class LoginController extends OpenController {

    @FXML
    private StackPane stack;

    @FXML
    private BorderPane border;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    private ProgressIndicator progress;

    @Override
    protected void employ() {
        progress = new ProgressIndicator();
        progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        username.focusedProperty().addListener((v)->username.getStyleClass().remove("invalid"));
        password.focusedProperty().addListener((v)->password.getStyleClass().remove("invalid"));
        username.setOnKeyPressed((ke)->{
            if(ke.getCode() == KeyCode.ENTER)password.requestFocus();
        });
        password.setOnKeyPressed((ke)->{
            if(ke.getCode() == KeyCode.ENTER)loginAction();
        });
    }

    @Override
    protected void dress() {}

    @FXML
    void cancelAction() {
        openStage().getStage().close();
    }

    @FXML
    void loginAction() {
        String usernameText = username.getText();
        String passwordText = password.getText();
        boolean valid = true;
        if(usernameText.isEmpty()){
            username.getStyleClass().add("invalid");
            valid = false;
        }
        if(passwordText.isEmpty()){
            password.getStyleClass().add("invalid");
            valid = false;
        }
        if(valid){
            responseWait();
            shop().deliver(HooktheoryDealer.username,usernameText);
            shop().deliver(HooktheoryDealer.password,passwordText);
            new Thread(this::requestToken).start();
        }
    }

    private void responseWait(){
        border.setDisable(true);
        border.setOpacity(0.5);
        stack.getChildren().add(progress);
    }

    private void responseFail(){
        border.setDisable(false);
        border.setOpacity(1.0);
        stack.getChildren().remove(progress);
        username.getStyleClass().add("invalid");
        password.getStyleClass().add("invalid");
    }


    private void requestToken() {
        if (shop().order(HooktheoryDealer.requestToken)) {
            Platform.runLater(() -> openStage().close());
        } else Platform.runLater(this::responseFail);
    }
}
