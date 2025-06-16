package studyapp.util;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UIUtil {
    private static final Image appIcon = new Image(UIUtil.class.getResourceAsStream("/icons/studyapp.png"));

    public static void applyAppIcon(Stage stage) {
        stage.getIcons().add(appIcon);
    }
}
