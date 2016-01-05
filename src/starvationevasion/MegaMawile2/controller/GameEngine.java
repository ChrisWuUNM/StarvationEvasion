package starvationevasion.MegaMawile2.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import starvationevasion.MegaMawile2.model.*;
import starvationevasion.MegaMawile2.net.NetworkHandler;
import starvationevasion.MegaMawile2.view.PolicyCardImageManager;

import java.awt.*;
import java.net.URL;
import java.util.*;

import static starvationevasion.MegaMawile2.model.NetworkStatus.*;

/**
 * Main Controller of the application. This is where most objects are initialized
 * and available for the the entire application.
 */
public class GameEngine implements GameController, Initializable, EventHandler<Event>
{
  //private final Player player;
  private AbstractPlayerController playerController;

  private NetworkHandler network;

  // private final Renderer simulationVisualization;
  // private MenuRenderer menuRenderer;
  private GameOptions options;
  private GUIDraftDrawDiscard guiDraftDrawDiscard;
  private float tryTime = 0f;
  private float loginTryTime = 0f;

  private GameStateData gameState = new GameStateData();
  private boolean state;

  @Override
  public void initialize(URL location, ResourceBundle resources)
  {
    options = new GameOptions();
    guiDraftDrawDiscard = new GUIDraftDrawDiscard();

    network = new NetworkHandler(options, gameState);
  }

  @Override
  public void update(float deltaTime)
  {
    // network try
    if (options.getNetworkStatus() == TRY)
    {
      options.setNetworkStatus(TRYING);
      network.createClient(playerController);
    }

    // player try
    if (getPlayer().getStatus() == TRY)
    {
      getPlayer().setStatus(TRYING);
    }

    // try and connect to server every 2s
    if (options.getNetworkStatus() == TRYING)
    {
      tryTime += 1;
    }

    // try and connect to server exeyer 2s
    if (getPlayer().getStatus() == TRYING)
    {
      loginTryTime += 1;
    }


    // if 2s has passes and server trying try to connect
    if (tryTime >= 200 && options.getNetworkStatus() == TRYING)
    {
      tryTime = 0;
      network.connectToServer();
    }

    // if 2s has passed and player is trying to login retry
    if (loginTryTime >= 200 && getPlayer().getStatus() == TRYING)
    {
      loginTryTime = 0f;
      network.loginToServer();
    }

    if (options.getNetworkStatus() == DISCONNECT)
    {
      network.disconnectFromServer();
    }

    playerController.update(deltaTime);
  }

  /**
   * Sets the ViewPort based upon the size of the given Rectangle.
   *
   * @param viewPort
   */
  public void setViewPort(Rectangle viewPort)
  {
    //menuRenderer.setViewPort(viewPort);
  }

  /**
   * Starts the server.
   */
  public void startServer()
  {
    options.setNetworkStatus(TRY);
  }

  /**
   * Re-instantiates a new HumanPlayerController with the current state.
   *
   * @param state
   */
  public void setState(boolean state)
  {
    this.state = state;
    if (!this.state)
    {
      playerController = new HumanPlayerController(gameState, options);
    }
  }

  /**
   * Sets options for the network.
   *
   * @param port
   * @param host
   */
  public void setOptions(String port, String host)
  {
    options.setHost(host);
    options.setPort(Integer.parseInt(String.valueOf(port)));
    options.setNetworkStatus(TRY);
  }

  /**
   * getter that returns the Network Handler.
   */
  public NetworkHandler getNetworkHandler()
  {
    return network;
  }

  /**
   * Returns the current player.
   */
  public Player getPlayer()
  {
    return playerController.getPlayer();
  }

  /**
   * Returns the current game state.
   */
  public GameStateData getGameState()
  {
    return gameState;
  }

  @Override
  public void handle(Event event)
  {

  }
}
