package brouse13.economia.Comando;

import brouse13.economia.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ZpuntosSetCmd implements CommandExecutor {
    Main main;
    public ZpuntosSetCmd(Main main) {this.main=main;}

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command c, @NotNull String l, @NotNull String[] a) {
        if(!(s instanceof Player)){
            if(a.length!= 2){return false;}

            if(Bukkit.getPlayer(a[0]) !=null){
                UUID uuid = Bukkit.getPlayer(a[0]).getUniqueId();
                main.mysql.setMonedas(uuid, Integer.parseInt(a[1]));
                return true;
            }else{
                for (OfflinePlayer ofp:Bukkit.getOfflinePlayers()) {
                    if(ofp.getName().equals(a[0])){
                        UUID uuid = ofp.getUniqueId();
                        main.mysql.setMonedas(uuid, Integer.parseInt(a[1]));
                        return true;
                    }
                }
                return false;
            }
        }
        Player p = (Player) s;

        if(!p.hasPermission("dbc.zpuntos.set")){p.sendMessage( "§cNo tienes permiso para ejecutar este comando");return false;}

        if(a.length!= 2){p.sendMessage("§a/zpset <jugador> <cantidad>");return false;}

        if(Bukkit.getPlayer(a[0]) !=null){
            UUID uuid = Bukkit.getPlayer(a[0]).getUniqueId();
            main.mysql.setMonedas(uuid, Integer.parseInt(a[1]));
            p.sendMessage("§aLos zpuntos de §6"+ p.getName() + "§ase han establecido en §6"+a[1]);
            return true;
        }else{
            for (OfflinePlayer ofp:Bukkit.getOfflinePlayers()) {
                if(ofp.getName().equals(a[0])){
                    UUID uuid = ofp.getUniqueId();
                    main.mysql.setMonedas(uuid, Integer.parseInt(a[1]));
                    p.sendMessage("§aLos zpuntos de §6"+ p.getName() + "§ase han establecido en §6"+a[1]);
                    return true;
                }
            }
            p.sendMessage("§cEl jugador§6 "+a[0] +" §cno existe");
            return false;
        }
    }
}
