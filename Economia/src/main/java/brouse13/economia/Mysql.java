package brouse13.economia;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class Mysql {
    private Connection connection;
    String host,database,username,password;
    String table;
    int port;
    Main main;

    public Mysql(Main main) {
        this.main=main;
    }

    public void IniciarMysql(){
        host = main.getConfig().getString("host");
        database = main.getConfig().getString("database");
        table = main.getConfig().getString("table");
        port = main.getConfig().getInt("port");
        username = main.getConfig().getString("username");
        password = main.getConfig().getString("password");

        if(password.equals("") || password.equals(" ")){password=null;}

        try {
            synchronized (this) {
                if(getConnection() != null && !getConnection().isClosed()) {return;}

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection(
                        "jdbc:mysql://"+this.host+ ":"+ this.port + "/"+ this.database, this.username, this.password));

                Bukkit.getConsoleSender().sendMessage("§aConexion establecida");
            }
        }catch (SQLException e) {e.printStackTrace();
        }catch (ClassNotFoundException e) {e.printStackTrace();}
    }

    public boolean existejugador(UUID uuid) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT * FROM " + this.table + " WHERE uuid=?");

            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {return true;}

        } catch (SQLException e) {e.printStackTrace();}
        return false;
    }

    public void createJugador(Player p) {
        try {
            if (!existejugador(p.getUniqueId())) {
                PreparedStatement insert = getConnection()
                        .prepareStatement("INSERT INTO " + table+ " (uuid,name) VALUES (?,?)");
                insert.setString(1, p.getUniqueId().toString());
                insert.setString(2, p.getName());
                insert.executeUpdate();
                main.getServer().getConsoleSender().sendMessage("§aSe ha anadido al gugador §6"+p.getName()+" §aa la base de datos");
            }
        } catch (SQLException e) {e.printStackTrace();}
    }

    public void setMonedas(UUID uuid, int monedas) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("UPDATE " + this.table+ " SET zpuntos=? WHERE UUID=?");
            statement.setInt(1, monedas);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {e.printStackTrace();}

    }

    public int getMonedas(UUID uuid) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT * FROM " + this.table+ " WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("zpuntos");
        } catch (SQLException e) {e.printStackTrace();return -1;}
    }

    public Connection getConnection() {return this.connection;}

    public void setConnection(Connection connection) {this.connection = connection;}
}
