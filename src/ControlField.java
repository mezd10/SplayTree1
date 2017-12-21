import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ControlField extends Pane{

    private TextField textField = new TextField();
    private Label message = new Label();

    private final double WINDOW_WIDTH = 1000;
    static final double BUTTON_WIDTH = 80;
    private final double BUTTON_AND_TEXTFIELD_HEIGHT = 30;
    static final double TEXTFIELD_WIDTH = BUTTON_WIDTH * 1.5;
    private final double TEXTFIELD_X = 20;
    private final double BUTTON_AND_TEXTFIELD_Y = 20;
    private final double TREE_Y = 80;
    private final double VERTEX_RADIUS = 24;

    private final Color SUCCESS_MESSAGE_COLOR = Color.PALEGREEN;
    private final Color ERROR_MESSAGE_COLOR = Color.valueOf("red");

    private final Font LARGE_FONT = new Font(16);

    ControlField() {
        addTextField();
        addButtonAdd();
        addButtonRemove();
        addButtonHeadSet();
        addButtonTailSet();
        addButtonClear();
        addMessage();
    }

    private void addTextField() {
        textField.setPrefSize(TEXTFIELD_WIDTH, BUTTON_AND_TEXTFIELD_HEIGHT);
        textField.setTranslateX(TEXTFIELD_X);
        textField.setTranslateY(BUTTON_AND_TEXTFIELD_Y);
        getChildren().add(textField);
    }

    private void addButtonAdd() {
        Button buttonAdd = new Button();
        buttonAdd.setText("Add");
        buttonAdd.setPrefSize(BUTTON_WIDTH, BUTTON_AND_TEXTFIELD_HEIGHT);
        buttonAdd.setTranslateX(TEXTFIELD_X + TEXTFIELD_WIDTH + 10);
        buttonAdd.setTranslateY(BUTTON_AND_TEXTFIELD_Y);
        buttonAdd.setOnAction(event -> {
            if (isDouble(textField.getText())) {
                Double element = Double.parseDouble(textField.getText());
                if (Main.tree.contains(element)) {
                    message.setTextFill(ERROR_MESSAGE_COLOR);
                    message.setText("Element " + element + " already exists");
                } else {
                    Main.tree.add(element);
                    message.setTextFill(SUCCESS_MESSAGE_COLOR);
                    message.setText("Element " + element + " was added");
                    Main.treeField.getChildren().clear();
                    Main.treeField.drawTree(Main.tree.getRoot(), WINDOW_WIDTH / 2 - VERTEX_RADIUS, TREE_Y, 240);
                }
            } else if (!textField.getText().isEmpty()) {
                message.setTextFill(ERROR_MESSAGE_COLOR);
                message.setText("Incorrect format");
            }
            textField.clear();
        });
        getChildren().add(buttonAdd);
    }

    private void addButtonRemove() {
        Button buttonRemove = new Button();
        buttonRemove.setText("Remove");
        buttonRemove.setPrefSize(BUTTON_WIDTH, BUTTON_AND_TEXTFIELD_HEIGHT);
        buttonRemove.setTranslateX(TEXTFIELD_X + TEXTFIELD_WIDTH + BUTTON_WIDTH + 20);
        buttonRemove.setTranslateY(BUTTON_AND_TEXTFIELD_Y);
        buttonRemove.setOnAction(event -> {
            if (isDouble(textField.getText())) {
                Double element = Double.parseDouble(textField.getText());
                if (Main.tree.contains(element)) {
                    Main.tree.remove(element);
                    message.setTextFill(SUCCESS_MESSAGE_COLOR);
                    message.setText("Element " + element + " was removed");
                    Main.treeField.getChildren().clear();
                    Main.treeField.drawTree(Main.tree.getRoot(), WINDOW_WIDTH / 2 - VERTEX_RADIUS, TREE_Y, 240);
                } else {
                    message.setTextFill(ERROR_MESSAGE_COLOR);
                    message.setText("There is no such element");
                }
            } else if (!textField.getText().isEmpty()) {
                message.setTextFill(ERROR_MESSAGE_COLOR);
                message.setText("Incorrect format");
            }
            textField.clear();
        });
        getChildren().add(buttonRemove);
    }



    private void addButtonHeadSet() {
        Button buttonHeadSet = new Button();
        buttonHeadSet.setText("HeadSet");
        buttonHeadSet.setPrefSize(BUTTON_WIDTH, BUTTON_AND_TEXTFIELD_HEIGHT);
        buttonHeadSet.setTranslateX(TEXTFIELD_X + TEXTFIELD_WIDTH + BUTTON_WIDTH - 70);
        buttonHeadSet.setTranslateY(BUTTON_AND_TEXTFIELD_Y + 40);
        buttonHeadSet.setOnAction(event -> {
            if (isDouble(textField.getText())) {
                Double element = Double.parseDouble(textField.getText());
                if (Main.tree.contains(element)) {
                    Main.tree.headSet(element);
                    message.setTextFill(SUCCESS_MESSAGE_COLOR);
                    message.setText("Elements" + Main.tree.headSet(element).toString());
                } else {
                    message.setTextFill(ERROR_MESSAGE_COLOR);
                    message.setText("There is no such element");
                }
            }
            else if (!textField.getText().isEmpty()) {
                message.setTextFill(ERROR_MESSAGE_COLOR);
                message.setText("Incorrect format");
            }
            textField.clear();
        });
        getChildren().add(buttonHeadSet);
    }

    private void addButtonTailSet() {
        Button buttonTailSet = new Button();
        buttonTailSet.setText("TailSet");
        buttonTailSet.setPrefSize(BUTTON_WIDTH, BUTTON_AND_TEXTFIELD_HEIGHT);
        buttonTailSet.setTranslateX(TEXTFIELD_X + TEXTFIELD_WIDTH + BUTTON_WIDTH + 20);
        buttonTailSet.setTranslateY(BUTTON_AND_TEXTFIELD_Y + 40);
        buttonTailSet.setOnAction(event -> {
            if (isDouble(textField.getText())) {
                Double element = Double.parseDouble(textField.getText());
                if (Main.tree.contains(element)) {
                    Main.tree.headSet(element);
                    message.setTextFill(SUCCESS_MESSAGE_COLOR);
                    message.setText("Elements" + Main.tree.tailSet(element).toString());
                } else {
                    message.setTextFill(ERROR_MESSAGE_COLOR);
                    message.setText("There is no such element");
                }
            }
            else if (!textField.getText().isEmpty()) {
                message.setTextFill(ERROR_MESSAGE_COLOR);
                message.setText("Incorrect format");
            }
            textField.clear();
        });
        getChildren().add(buttonTailSet);
    }

    private void addButtonClear() {
        Button buttonClear = new Button();
        buttonClear.setText("Clear");
        buttonClear.setPrefSize(BUTTON_WIDTH, BUTTON_AND_TEXTFIELD_HEIGHT);
        buttonClear.setTranslateX(WINDOW_WIDTH - BUTTON_WIDTH - 10);
        buttonClear.setTranslateY(BUTTON_AND_TEXTFIELD_Y);
        buttonClear.setOnAction(event -> {
            Main.tree = new SplayTree();
            Main.treeField.getChildren().clear();
            message.setTextFill(SUCCESS_MESSAGE_COLOR);
            message.setText("Tree was cleared");
        });
        getChildren().add(buttonClear);
    }

    private void addMessage() {
        message.setTranslateX(TEXTFIELD_X + TEXTFIELD_WIDTH + BUTTON_WIDTH * 2 + 50);
        message.setTranslateY(BUTTON_AND_TEXTFIELD_Y + 5);
        message.setFont(LARGE_FONT);
        getChildren().add(message);
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
