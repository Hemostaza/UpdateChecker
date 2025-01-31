package com.hemostaza.updateChecker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateCheckerListener implements Listener {

    @EventHandler
    public void notifyOnJoin(PlayerJoinEvent playerJoinEvent) {

        Player player = playerJoinEvent.getPlayer();
        if (player.isOp()) {
            UpdateChecker update = UpdateChecker.getInstance();
            if(update.ResultMessage()!=null){
                player.sendMessage(update.ResultMessage());
            }
        }
    }
}
