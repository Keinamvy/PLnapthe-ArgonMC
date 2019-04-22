package ru.minejs.rest.api;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class Config {
    private String token;
    private Integer port;
    Config(FileConfiguration configuration) {
        token = configuration.getString("rest-api.token");
        port = configuration.getInt("rest-api.port");
    }
}