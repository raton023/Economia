package brouse13.economia.Comando;

import brouse13.economia.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZpuntosTopCmd implements CommandExecutor {
    Main main;

    public ZpuntosTopCmd(Main main) {this.main=main;}

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command c, @NotNull String l, @NotNull String[] a) {
        if(!(s instanceof Player)){
            Bukkit.getConsoleSender().sendMessage("Solo disponible para jugadores");
            return false;
        }
        Player p = (Player) s;

        try {
            PreparedStatement statement = main.mysql.getConnection()
                    .prepareStatement("SELECT * FROM zpuntos ORDER BY zpuntos DESC LIMIT 10");
            ResultSet results = statement.executeQuery();
            results.next();
            String message = "";
            int i=1;
            do{
                message+= i+ "- "+results.getString("name")+": "+results.getInt("zpuntos")+ "\n";
                i++;
            }while (results.next());

            p.sendMessage(message);
            return true;
        } catch (SQLException e) {e.printStackTrace();return false;}
    }
}
