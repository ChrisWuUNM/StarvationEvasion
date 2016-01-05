package starvationevasion.MegaMawile2.net;


import starvationevasion.MegaMawile2.model.GameOptions;
import starvationevasion.MegaMawile2.model.Player;
import starvationevasion.MegaMawile2.model.GameStateData;

import java.util.Scanner;


public class HumanClient extends AbstractClient
{
  // Read in lines from console.
  Scanner keyboard;
  public HumanClient(GameOptions options, Player player, GameStateData gameState)
  {
    super(options, player, gameState);
    keyboard = new Scanner(System.in);
  }
}
