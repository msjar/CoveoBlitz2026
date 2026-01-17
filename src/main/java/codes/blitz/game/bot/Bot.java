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
    if(myTeam.spawners().size() < 2 && !myTeam.spores().isEmpty()){
      actions.add(spawnerManager.createSpawner(findBestSpawnerLocation(gameMessage, myTeam)));
    }
    if(myTeam.nutrients() >= spawnerManager.INITIAL_SPORE_BIOMASS && !myTeam.spawners().isEmpty()){
      actions.add(spawnerManager.produceSpore(myTeam.nutrients()));
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

  private Spore findBestSpawnerLocation(TeamGameState gameMessage, TeamInfo myTeam){
      Spore bestSpore = myTeam.spores().getFirst();
      int maxAvailableNutrients = 0;
      for (Spore s : myTeam.spores()) {
        //count available nutrients near spore

        int currentNutrients = gameMessage.world().map().nutrientGrid()[s.position().x()][s.position().y()];
        for (int i = 1; i < 5; i++) {
            if ((s.position().x() + i >= gameMessage.world().map().width()) ||
                s.position().x() - i < 0 ||
                s.position().y() + i >= gameMessage.world().map().height() ||
                s.position().y() - i < 0)
                continue;
            currentNutrients += gameMessage.world().map().nutrientGrid()[s.position().x() + i][s.position().y()];
            currentNutrients += gameMessage.world().map().nutrientGrid()[s.position().x() - i][s.position().y()];
            currentNutrients += gameMessage.world().map().nutrientGrid()[s.position().x()][s.position().y() + i];
            currentNutrients += gameMessage.world().map().nutrientGrid()[s.position().x()][s.position().y() - i];
            if (currentNutrients > maxAvailableNutrients) {
                maxAvailableNutrients = currentNutrients;
                bestSpore = s;
            }
        }
      }
      return bestSpore;
  }
}
