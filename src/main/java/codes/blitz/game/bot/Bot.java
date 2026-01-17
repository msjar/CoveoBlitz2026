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
    new SporeMoveToAction(
            myTeam.spores().getFirst().id(),
            new Position(
                    random.nextInt(gameMessage.world().map().width()),
                    random.nextInt(gameMessage.world().map().height())));
    if(!firstActionDone){
      actions.add(addSpawner(myTeam.spores().getFirst(), myTeam.nextSpawnerCost()));
    }
    if(myTeam.nutrients() >= spawnerManager.INITIAL_SPORE_BIOMASS && !myTeam.spawners().isEmpty()){
      actions.add(spawnerManager.produceSpore());
    }

    // You can clearly do better than the random actions above. Have fun!!
    return actions;
  }

  public Action addSpawner(Spore spore, int nextSpawnerCost){
    if(spore.biomass() >= nextSpawnerCost){
      System.out.println("Spawner");
      firstActionDone = true;
      return new SporeCreateSpawnerAction(spore.id());
    }
    return null;
  }
}
