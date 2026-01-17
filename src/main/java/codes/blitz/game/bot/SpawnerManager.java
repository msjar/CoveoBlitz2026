package codes.blitz.game.bot;

import codes.blitz.game.generated.*;

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

    /*
    * Produces a spore. Currently, the spore is created from the first spawner.
    * */
    public Action produceSpore(){
        return new SpawnerProduceSporeAction(currentSpawners.getFirst().id(), INITIAL_SPORE_BIOMASS);
    }

    public Action createSpawner(Spore spore){
        return new SporeCreateSpawnerAction(spore.id());
    }
}
