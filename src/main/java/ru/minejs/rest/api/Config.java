package ru.minejs.rest.api;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class Config {
    private final String token;
    private final Integer port;
    Config(FileConfiguration configuration) {
        token = configuration.getString("rest-api.token");
        port = configuration.getInt("rest-api.port");
    }

    public String getToken() {
        return token;
    }

    public Integer getPort() {
        return port;
    }
}