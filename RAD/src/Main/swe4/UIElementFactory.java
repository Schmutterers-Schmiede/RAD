package swe4;

import javafx.scene.control.Button;

public class UIElementFactory {
  public static Button createButton(String id, String text) {
    Button button = new Button(text);
    button.setId(id);

    return button;
  }
}
