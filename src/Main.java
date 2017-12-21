import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private final double WINDOW_WIDTH = 1000;
    private final double WINDOW_HEIGHT = 600;

    static SplayTree tree = new SplayTree();
    static Pane mainField = new Pane();
    static ControlField controlField = new ControlField();
    static TreeField treeField = new TreeField();


    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setTitle("Splay tree");
        mainField.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainField.setStyle("-fx-background-color: black");
        mainField.getChildren().addAll(treeField, controlField);
        stage.setScene(new Scene(mainField));
        stage.show();
    }
}
