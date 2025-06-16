package studyapp.util;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.scene.control.Alert;

import studyapp.controller.CustomAlertController;
import javafx.scene.image.Image;


public class UIUtil {
    private static final Image appIcon = new Image(UIUtil.class.getResourceAsStream("/icons/StudyApp.png"));

    public static void applyAppIcon(Stage stage) {
        stage.getIcons().add(appIcon);
    }

    public static void showAlert(Window owner, Alert.AlertType type, String title, String message) {
        try {
            FXMLLoader loader = new FXMLLoader(UIUtil.class.getResource("/view/General - CustomAlert.fxml"));
            Parent root = loader.load();

            CustomAlertController controller = loader.getController();
            controller.setContent(title, message);

            Stage alertStage = new Stage();
            controller.setStage(alertStage);
            alertStage.initOwner(owner);
            alertStage.initModality(Modality.WINDOW_MODAL);
            alertStage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(UIUtil.class.getResource("/css/General - CustomAlert.css").toExternalForm());
            alertStage.setScene(scene);

            alertStage.setOnShown(e -> {
                Platform.runLater(() -> {
                    if (owner != null) {
                        double centerY = owner.getY() + (owner.getHeight() - alertStage.getHeight()) / 2;
                        alertStage.setY(centerY);
                    }
                });
            });

            alertStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
