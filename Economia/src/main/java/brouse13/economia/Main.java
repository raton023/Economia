package brouse13.economia;

import brouse13.economia.Comando.*;
import brouse13.economia.Listeners.LoginPlayerEvt;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Main extends JavaPlugin{
    public Mysql mysql;

    @Override
    public void onEnable() {
        if(!getDataFolder().exists()){createConfig();}
        if(getConfig().getBoolean("enabled")==false){Bukkit.getPluginManager().disablePlugin(this);return;}
        mysql = new Mysql(this);
        mysql.IniciarMysql();
        Bukkit.getPluginManager().registerEvents(new LoginPlayerEvt(this),this);
        this.getCommand("zpuntos").setExecutor(new ZpuntosCmd(this));
        this.getCommand("zpuntosset").setExecutor(new ZpuntosSetCmd(this));
        this.getCommand("zpuntostop").setExecutor(new ZpuntosTopCmd(this));
        this.getCommand("zpuntostake").setExecutor(new ZpuntosTakeCmd(this));
        this.getCommand("zpuntosgive").setExecutor(new ZpuntosGiveCmd(this));
    }

    private void createConfig() {
        try {

            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                saveDefaultConfig();
                this.getConfig().options().copyDefaults(false);
            } else {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                    this.getConfig().load(reader);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (this.getConfig() == null) {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
