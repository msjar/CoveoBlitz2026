package codes.blitz.game.bot;

import codes.blitz.game.generated.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Bot {
  Random random = new Random();
  private boolean firstActionDone  = false;
  private SpawnerManager spawnerManager;

  public Bot() {
    spawnerManager = new SpawnerManager();
    System.out.println("Initializing your super mega duper bot");
  }

  /*
   * Here is where the magic happens, for now the moves are not very good. I bet you can do better ;)
   */
  public List<Action> getActions(TeamGameState gameMessage) {
    List<Action> actions = new ArrayList<>();
    TeamInfo myTeam = gameMessage.world().teamInfos().get(gameMessage.yourTeamId());
    spawnerManager.setCurrentSpawners(myTeam.spawners());
    if(!firstActionDone && myTeam.spores().getFirst().biomass() >= myTeam.nextSpawnerCost()){
      actions.add(spawnerManager.createSpawner(myTeam.spores().getFirst()));
      firstActionDone = true;
    }
    if(myTeam.nutrients() >= spawnerManager.INITIAL_SPORE_BIOMASS && !myTeam.spawners().isEmpty()){
      System.out.println("Spore spawned");
      actions.add(spawnerManager.produceSpore());
    }

    if(!myTeam.spores().isEmpty()){
      //spore biomass > 2
      actions.add(new SporeMoveToAction(
              myTeam.spores().getFirst().id(),
              new Position(
                      gameMessage.world().map().width() -1,
                      gameMessage.world().map().height() - 1)));
    }


    System.out.println(myTeam.spores().getLast().id());

    // You can clearly do better than the random actions above. Have fun!!
    return actions;
  }
}
