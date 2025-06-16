package studyapp.util;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UIUtil {
    private static final Image appIcon = new Image(UIUtil.class.getResourceAsStream("/icons/studyapp.png"));

    /**
     * Applies the application icon to a stage
     * @param stage the stage to which the icon will be applied
     */
    public static void applyAppIcon(Stage stage) {
        stage.getIcons().add(appIcon);
    }
}
