package de.verfxgbar.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileManager {

    private static File ranks, permissions, groupPermissions;

    public static void createFiles() {
        File dir = new File("/home/paper/plugins/FIS/");
        dir.mkdirs();
        ranks = new File("/home/paper/plugins/FIS/ranks.yml");
        permissions = new File("/home/paper/plugins/FIS/permissions.yml");
        groupPermissions = new File("/home/paper/plugins/FIS/groupPermissions.yml");

        try {
            if (!ranks.exists())
                ranks.createNewFile();
            if (!permissions.exists())
                permissions.createNewFile();
            if (!groupPermissions.exists())
                groupPermissions.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getPermissionsFile() {
        return permissions;
    }

    public static File getRankFile() {
        return ranks;
    }

    public static File getGroupPermissionsFile() {
        return groupPermissions;
    }

    public static HashMap<Player, FIS_RANK> getRanks() {
        HashMap<Player, FIS_RANK> map = new HashMap<>();
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(ranks);
        Bukkit.getOnlinePlayers().forEach(player -> {
            String result = cfg.getString(player.getName());
            if (result == null) {
                map.put(player, FIS_RANK.PLAYER);
            } else if (result.equalsIgnoreCase("player"))
                map.put(player, FIS_RANK.PLAYER);
            else if (result.equalsIgnoreCase("supporter"))
                map.put(player, FIS_RANK.SUPPORTER);
            else if (result.equalsIgnoreCase("moderator"))
                map.put(player, FIS_RANK.MODERATOR);
            else if (result.equalsIgnoreCase("developer"))
                map.put(player, FIS_RANK.DEVELOPER);
            else if (result.equalsIgnoreCase("admin"))
                map.put(player, FIS_RANK.ADMIN);
            else
                map.put(player, FIS_RANK.PLAYER);
        });

        return map;
    }
}

