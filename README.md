## MineCraft Bukkit plugin.
Created by lazybones template: 
https://github.com/JavaGrinko/lazybones-templates/tree/master/templates/bukkit-plugin

Create the same project:
```
lazybones create https://dl.bintray.com/javagrinko/lazybones-templates/bukkit-plugin-template-0.1.zip new-bukkit-plugin
```

### Project structure:
* src
    * main
        * java
            * com.test
                * Config.java - config class
                * Plugin.java - main plugin class
        * resources
            * config.yml - plugin parameters
            * language.yml - messages
            * plugin.yml - Bukkit config
* build.gradle
* gradlew
* gradlew.bat
* README.md
* settings.gradle
* gradle
    * wrapper
        * gradle-wrapper.jar
        * gradle-wrapper.properties
                
How to build and start plugin:
 * type "./gradlew jar" in console
 * copy build/libs/*.jar to folder call "plugins" on the server.
 
Finally, if all is ok you should see this message in minecraft console:
 [20:16:56 INFO]: [TestPlugin] Enabling TestPlugin v1.0.0
 [20:16:56 INFO]: [TestPlugin] Hello World