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
      actions.add(spawnerManager.produceSpore());
    }


    for(Spore s : myTeam.spores()){
      if(s.biomass() > 2){
        NutrientFinder nutrientFinder = new NutrientFinder(gameMessage.world().map().nutrientGrid(), s.position().x(), s.position().y());

        Position position = nutrientFinder.findNearestNutrientTileFromSpore(gameMessage.world().ownershipGrid(), gameMessage.yourTeamId());

        actions.add(new SporeMoveToAction(s.id(), position));
      }

    }
    // You can clearly do better than the random actions above. Have fun!!
    return actions;
  }
}
