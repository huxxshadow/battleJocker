package Server.Controller;

import Server.Model.Entity.PlayerEntity;
import Server.Model.Entity.RoundEntity;

public class RoundController {

    public RoundEntity roundEntity;


    public RoundController(PlayerController playerController) {
        roundEntity = new RoundEntity();
        roundEntity.players.add(playerController);
    }

    public void join(PlayerController playerController) {
        roundEntity.players.add(playerController);
    }


    public void updateAllPlayers() {
        for (PlayerController player : roundEntity.players) {
            player.update(roundEntity);
        }
    }
}
