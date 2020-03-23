package brouse13.economia.Listeners;

import brouse13.economia.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginPlayerEvt implements Listener {
    Main main;

    public LoginPlayerEvt(Main main) {
        this.main = main;
    }

    @EventHandler
    public void PlayerLogin(PlayerLoginEvent e){
        Player p = e.getPlayer();
        if(!main.mysql.existejugador(p.getUniqueId())){
            main.mysql.createJugador(p);
            p.sendMessage(ChatColor.GREEN+"Creando cuenta de Zpuntos...");
        }

    }
}
