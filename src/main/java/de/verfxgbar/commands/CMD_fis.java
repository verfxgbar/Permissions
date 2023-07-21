package de.verfxgbar.commands;

import de.verfxgbar.utils.FISPermissions;
import de.verfxgbar.utils.FIS_RANK;
import de.verfxgbar.utils.FileManager;
import de.verfxgbar.utils.NameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMD_fis implements CommandExecutor, TabCompleter {
    @Override
    // fis <player> <rank/perms/group> <set/add/remove> <FIS_RANK/PERMISSION>
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length) {
            case 0, 3 -> {
                sendErrorSyntax(sender);
            }

            // fis <player>
            case 1 -> {
                String playerName = args[0];
                ArrayList<String> permsListe = FISPermissions.getPermissions(playerName);
                String s = "4FIS §7׀ Hier sind alle Permissions vom Spieler " + playerName + " \n";
                for (String s1 : permsListe)
                    s += ChatColor.GRAY + s1 + "\n";
            }

            // fis <player><group> <rank/perms/group>
            case 2 -> {
                String playerName = args[0], arg1 = (args[1].toLowerCase().equalsIgnoreCase("rank") || args[1].toLowerCase().equalsIgnoreCase("perms") ? args[1].toLowerCase() : "perms");
                if (arg1.equalsIgnoreCase("rank")) {
                    FIS_RANK rank = FISPermissions.getRank(playerName);
                    if (rank == FIS_RANK.NONE) {
                        sender.sendMessage("§4FIS §7׀ §cDer Spieler konnte leider nicht gefunden werden!");
                    } else sender.sendMessage("§4FIS §7׀ Der Spieler hat den Rang " + rank + "!");
                }
                if (arg1.equalsIgnoreCase("perms") || arg1.equalsIgnoreCase("group")) {
                    sendErrorSyntax(sender);
                }
            }

            case 4 -> {
                String playerName = args[0], arg1 = (args[1].toLowerCase().equalsIgnoreCase("rank") || args[1].toLowerCase().equalsIgnoreCase("perms") || args[1].toLowerCase().equalsIgnoreCase("group") ? args[1].toLowerCase() : "none"), arg2 = (args[2].equalsIgnoreCase("set") || args[2].equalsIgnoreCase("add") || args[2].equalsIgnoreCase("remove") ? args[2].toLowerCase() : "none"), arg3 = args[3];
                if (arg1.equalsIgnoreCase("rank")) {
                    if (arg2.equalsIgnoreCase("set")) {
                        FIS_RANK rank = (Arrays.stream(FIS_RANK.values()).filter(r -> r.toString().equalsIgnoreCase(arg3)).toList().isEmpty() ? FIS_RANK.NONE : Arrays.stream(FIS_RANK.values()).filter(r -> r.toString().equalsIgnoreCase(arg3)).toList().get(0));
                        FISPermissions.setPlayerRank(playerName, rank);
                        sender.sendMessage("§4FIS §7׀ Der Spieler hat jetzt den Rang " + rank.toString() + "!");
                    } else sendErrorSyntax(sender);
                } else if (arg1.equalsIgnoreCase("perms")) {
                    if (arg2.equalsIgnoreCase("add") || arg2.equalsIgnoreCase("set")) {
                        FISPermissions.addPermission(playerName, arg3);
                        sender.sendMessage("§4FIS §7׀ Der Spieler hat die Permissions erhalten!");
                    }
                    if (arg2.equalsIgnoreCase("remove")) {
                        FISPermissions.removePermission(playerName, arg3);
                        sender.sendMessage("§4FIS §7׀ Der Spieler hat die Permissions entzogen bekommen!");
                    }
                } else if (arg1.equalsIgnoreCase("group")) {
                    FIS_RANK rank = (Arrays.stream(FIS_RANK.values()).filter(r -> r.toString().equalsIgnoreCase(playerName)).toArray().length == 0 ? null : (FIS_RANK) Arrays.stream(FIS_RANK.values()).filter(r -> r.toString().equalsIgnoreCase(playerName)).toArray()[0]);
                    if (rank == null) {
                        sender.sendMessage("§4FIS §7׀ Diese Gruppe gibt es leider nicht.");
                        return true;
                    }
                    if (arg2.equalsIgnoreCase("add")) {
                        FISPermissions.addGroupPermission(rank, arg3);
                        sender.sendMessage("§4FIS §7׀ Die Permission wurde hinzugefügt!");
                    } else if (arg2.equalsIgnoreCase("remove")) {
                        FISPermissions.removeGroupPermission(rank, arg3);
                        sender.sendMessage("§4FIS §7׀ Die Permission wurde entfernt!");
                    } else {
                        sendErrorSyntax(sender);
                    }
                }
            }
        }
        NameManager.updateNames();
        return true;
    }

    private void sendErrorSyntax(CommandSender sender) {
        sender.sendMessage("§4FIS §7׀ §cFalscher Syntax! Bitte benutze /fix <spieler> <rank/perms> <set/add/remove> <FIS_RANK/PERMISSION>");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        switch (args.length) {
            case 1:
                Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
                return list;
            case 2:
                list.add("rank");
                list.add("perms");
                return list;
            case 3:
                list.add("set");
                list.add("add");
                list.add("remove");
                return list;
            case 4:
                if (args[1].equalsIgnoreCase("rank"))
                    Arrays.stream(FIS_RANK.values()).forEach(v -> list.add(v.toString()));
                return list;
        }
        return list;
    }
}
