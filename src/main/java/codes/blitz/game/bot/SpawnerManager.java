package codes.blitz.game.bot;

import codes.blitz.game.generated.Action;
import codes.blitz.game.generated.Spawner;
import codes.blitz.game.generated.SpawnerProduceSporeAction;

import java.util.List;

public class SpawnerManager {
    private List<Spawner> currentSpawners;
    public final int INITIAL_SPORE_BIOMASS = 5;

    public SpawnerManager() {
    }

    public List<Spawner> getCurrentSpawners() {
        return currentSpawners;
    }

    public void setCurrentSpawners(List<Spawner> currentSpawners) {
        this.currentSpawners = currentSpawners;
    }

    public Action produceSpore(){
        return new SpawnerProduceSporeAction(currentSpawners.getFirst().id(), INITIAL_SPORE_BIOMASS);
    }
}
