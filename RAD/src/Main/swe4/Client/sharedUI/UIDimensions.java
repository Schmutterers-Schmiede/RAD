package swe4.Client.sharedUI;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class UIDimensions {
  public static Insets windowPadding() {
    return new Insets(10, 10, 10, 10);
  }

  public static double buttonSpacing() {
    return 8;
  }

  public static double containerSpacing() {
    return 10;
  }

  public static double gridPaneSpacing() {
    return 5;
  }

  public static Region createSpacer() {
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    return spacer;
  }

  public static double buttonWidthShort(){
    return 80;
  }

  public static double buttonWidthMedium() {
    return 120;
  }

  public static double buttonWidthLong() {
    return 160;
  }
}
