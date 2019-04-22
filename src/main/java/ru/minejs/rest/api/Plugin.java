package ru.minejs.rest.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

import static spark.Spark.*;

public class Plugin extends JavaPlugin {

    private static FileConfiguration messages;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadMessages(this);
        Config config = new Config(getConfig());
        port(config.getPort());
        get("/health", (req, res) -> {
            res.header("Content-Type", "application/json");
            return "{ \"status\" : \"UP\"}";
        });
        post("/restart", (req, res) -> {
            String authorization = req.headers("Authorization");
            if (authorization == null) {
                res.status(401);
                return "Authorization header required!";
            }
            String reason = checkAuth(config, authorization);
            if (reason != null) {
                res.status(401);
                return reason;
            }
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "Restart server...");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
            res.header("Content-Type", "application/json");
            return " { \"status\" : \"ok\" }";
        });
    }

    private String checkAuth(Config config, String authorization) {
        String[] parts = authorization.split(" ");
        String type = parts[0];
        String data = parts[1];
        if ("Basic".equalsIgnoreCase(type)) {
            String decode = new String(Base64.getDecoder().decode(data));
            if (!decode.equalsIgnoreCase(config.getToken())) {
                return "Invalid token";
            }
        } else {
            return "Authorization type not support. Try Basic.";
        }
        return null;
    }

    @Override
    public void onDisable() {
        stop();
    }

    private void loadMessages(Plugin plugin) {
        File languageFile = new File(plugin.getDataFolder(), "language.yml");
        if (languageFile.exists()) {
            messages = YamlConfiguration.loadConfiguration(languageFile);
        } else {
            messages = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(plugin.getResource("language.yml")));
            try {
                messages.save(languageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMessage(String key) {
        return messages.getString(key);
    }
}
