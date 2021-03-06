package starvationevasion.MegaMawile2.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StartupScreen extends Stage
{

  public StartupScreen(MenuController menuController)
  {
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("popups/StartupPopup.fxml"));
    loader.setController(menuController);

    initStyle(StageStyle.UNDECORATED);

    TitledPane pane = null;
    try
    {
      pane = loader.load();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    setScene(new Scene(pane));
  }
}
