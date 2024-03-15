package ru.minejs.rest.api;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import spark.Spark;

import static spark.Spark.*;

public class Main extends JavaPlugin {
    public static ConfigReader CodeConfig;
    public static FileConfiguration codeConfig;

    public String run(Player player) {
        CodeGen config = CodeGen.pattern("###-###-###");
        String code = RandCode.generate(config);
        codeConfig.set("code." + code + ".time", System.currentTimeMillis());
        codeConfig.set("code." + code + ".player", player.getUniqueId().toString());
        ConfigReader.save(CodeConfig);
        TextComponent message = new TextComponent("Click to copy: " + code);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, code));
        player.spigot().sendMessage(message);
        return "SUCCEED";
    }

    @Override
    public void onEnable() {
        CodeConfig = new ConfigReader(this, "./", "code.yml");
        CodeConfig.saveDefaultConfig();
        codeConfig = CodeConfig.getConfig();
        saveDefaultConfig();
        Config config = new Config(getConfig());
        port(config.getPort());
        post("/checkUser", (request, response) -> {
            Player output = Bukkit.getPlayer(request.queryParams("username"));
            if (output != null) {
                run(output);
            }
            return "FAILED";
        });
        post("/code", (request, response) -> {
            String code = request.queryParams("code");
            if (code == null) {
                return "FAILED";
            }
            if (codeConfig.getString("code." + code + ".time") == null) {
                return "FAILED";
            }
            if (System.currentTimeMillis() - codeConfig.getLong("code." + code + ".time") < 300000) {
                codeConfig.set("code." + code, null);
                ConfigReader.save(CodeConfig);
                return "SUCCEED";
            }
            return "FAILED";
        });
    }

    @Override
    public void onDisable() {
        Spark.stop();
    }


}

