package brouse13.economia.Comando;

import brouse13.economia.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ZpuntosGiveCmd implements CommandExecutor {
    Main main;
    public ZpuntosGiveCmd(Main main) {this.main=main;}

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command c, @NotNull String l, @NotNull String[] a) {
        if(!(s instanceof Player)){
            if(a.length !=2){return false;}

            if(Bukkit.getPlayer(a[0]) !=null){
                UUID uuid = Bukkit.getPlayer(a[0]).getUniqueId();
                int monedas = main.mysql.getMonedas(uuid);

                main.mysql.setMonedas(uuid, (monedas+Integer.parseInt(a[1])));
                return true;
            }else{
                for (OfflinePlayer ofp:Bukkit.getOfflinePlayers()) {
                    if(ofp.getName().equals(a[0])){
                        UUID uuid = ofp.getUniqueId();
                        int monedas = main.mysql.getMonedas(uuid);

                        main.mysql.setMonedas(uuid, (monedas+Integer.parseInt(a[1])));
                    }
                }
                return false;
            }
        }
        Player p = (Player) s;

        if(!p.hasPermission("dbc.economia.give")){p.hasPermission("§cNo tienes permiso para ejecutar el comando");return false;}

        if(a.length !=2){p.sendMessage("§f/zpgive <jugador> <cantidad>");return false;}

        if(Bukkit.getPlayer(a[0]) !=null){
            UUID uuid = Bukkit.getPlayer(a[0]).getUniqueId();
            int monedas = main.mysql.getMonedas(uuid);

                main.mysql.setMonedas(uuid, (monedas+Integer.parseInt(a[1])));
                p.sendMessage("§aSe ha añadido §6"+ a[1]+" §azpuntos a "+ p.getName() + "" +
                    "\n§aBalance actual: §6"+(monedas+Integer.parseInt(a[1])));

            return true;
        }else{
            for (OfflinePlayer ofp:Bukkit.getOfflinePlayers()) {
                if(ofp.getName().equals(a[0])){
                    UUID uuid = ofp.getUniqueId();
                    int monedas = main.mysql.getMonedas(uuid);

                        main.mysql.setMonedas(uuid, (monedas+Integer.parseInt(a[1])));
                        p.sendMessage("§aSe ha añadido §6"+ a[1]+" §azpuntos a "+ p.getName() + "" +
                            "\n§aBalance actual: §6"+(monedas+Integer.parseInt(a[1])));

                }
            }
            p.sendMessage("§cEl jugador §6"+a[0] +"§c no existe");
            return false;
        }
    }
}
