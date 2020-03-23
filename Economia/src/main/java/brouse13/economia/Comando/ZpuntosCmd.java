package brouse13.economia.Comando;

import brouse13.economia.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ZpuntosCmd implements CommandExecutor {
    Main main;

    public ZpuntosCmd(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command c, @NotNull String l, @NotNull String[] a) {
        if(!(s instanceof Player)){
            Bukkit.getConsoleSender().sendMessage("Solo disponible para jugadores");
            return false;
        }
        Player p = (Player) s;

        if(a.length == 0){
            p.sendMessage("§aTienes §6"+main.mysql.getMonedas(p.getUniqueId()) + " §aZpuntos");
            return true;
        }else if(a.length==1){
            if(p.hasPermission("dbc.zpuntos.others")){
                p.sendMessage("§aZpuntos de §6"+ p.getName()+ "§a: §6"+main.mysql.getMonedas(p.getUniqueId()));
                return true;
            }
            p.sendMessage("§fUso: /zpuntos");
            return false;
        }else{
            if(p.hasPermission("dbc.zpuntos.others")){
                p.sendMessage("§f/zpuntos <jugador>")
                ;return true;
            }else{
                p.sendMessage("§f/zpuntos");
                return true;
            }
        }
    }
}
