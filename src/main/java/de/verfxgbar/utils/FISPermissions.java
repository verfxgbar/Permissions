package de.verfxgbar.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FISPermissions {

    public final static ArrayList<String> getPermissions(String playerName) {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(FileManager.getPermissionsFile());
        ArrayList<String> perms = (ArrayList<String>) cfg.getList(playerName);
        return perms;
    }

    public final static boolean hasPermission(String playerName, String permission) {
        return getPermissions(playerName).contains(permission);
    }

    public final static void addPermission(String playerName, String permission) {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(FileManager.getPermissionsFile());
        ArrayList<String> list = (ArrayList<String>) cfg.getList(playerName);
        list.add(permission);
        cfg.set(playerName, list);

        try {
            cfg.save(FileManager.getPermissionsFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final static void removePermission(String playerName, String permission) {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(FileManager.getPermissionsFile());
        ArrayList<String> list = (ArrayList<String>) cfg.getList(playerName);
        list.remove(permission);

        if (list.isEmpty()) cfg.set(playerName, null);
        else cfg.set(playerName, list);

        try {
            cfg.save(FileManager.getPermissionsFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final static FIS_RANK getRank(String playerName) {
        HashMap<Player, FIS_RANK> ranks = FileManager.getRanks();

        FIS_RANK rank = FIS_RANK.NONE;
        for (Player player : ranks.keySet())
            if (player.getName().equalsIgnoreCase(playerName))
                rank = ranks.get(player);
        return rank;
    }

    public final static void setPlayerRank(String playerName, FIS_RANK rank) {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(FileManager.getRankFile());
        cfg.set(playerName, rank.toString());
        System.out.println("NEUER RANG -> " + rank.toString());

        try {
            cfg.save(FileManager.getRankFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NameManager.updateNames();
    }

    public final static void registerPlayer(Player player) {
        YamlConfiguration rankcfg = YamlConfiguration.loadConfiguration(FileManager.getRankFile());
        YamlConfiguration permcfg = YamlConfiguration.loadConfiguration(FileManager.getPermissionsFile());

        if (rankcfg.get(player.getName()) == null)
            rankcfg.set(player.getName(), FIS_RANK.PLAYER.toString());

        if (permcfg.get(player.getName()) == null)
            permcfg.set(player.getName(), new ArrayList<String>());

        try {
            rankcfg.save(FileManager.getRankFile());
            permcfg.save(FileManager.getPermissionsFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final static List<String> getGroupPermissions(FIS_RANK rank) {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(FileManager.getGroupPermissionsFile());
        List<String> groupPermsList = (List<String>) cfg.getList(rank.toString());
        if (groupPermsList != null)
            return groupPermsList;
        else
            return new ArrayList<String>();
    }

    public final static void removeGroupPermission(FIS_RANK rank, String permission) {
        if (getGroupPermissions(rank).contains(permission)) {
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(FileManager.getGroupPermissionsFile());
            List<String> groupPermsList = (List<String>) cfg.getList(rank.toString());
            groupPermsList.remove(permission);
            cfg.set(rank.toString(), groupPermsList);
            try {
                cfg.save(FileManager.getGroupPermissionsFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final static boolean groupHasPermission(FIS_RANK rank, String permission) {
        return getGroupPermissions(rank).contains(permission);
    }

    public final static void addGroupPermission(FIS_RANK rank, String permission) {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(FileManager.getGroupPermissionsFile());
        List<String> groupPermsList = ((List<String>) cfg.getList(rank.toString()) == null ? new ArrayList<>() : (List<String>) cfg.getList(rank.toString()));
        groupPermsList.add(permission);
        cfg.set(rank.toString(), groupPermsList);

        try {
            cfg.save(FileManager.getGroupPermissionsFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
