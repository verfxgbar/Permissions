package de.verfxgbar.utils;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class NameManager {

    public static void updateNames() {
        HashMap<Player, FIS_RANK> ranks = FileManager.getRanks();
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Team player = sb.registerNewTeam("99");
        Team supporter = sb.registerNewTeam("90");
        Team moderator = sb.registerNewTeam("80");
        Team developer = sb.registerNewTeam("50");
        Team admin = sb.registerNewTeam("10");
        player.setPrefix(FIS_RANK.PLAYER.getPrefix());
        supporter.setPrefix(FIS_RANK.SUPPORTER.getPrefix());
        moderator.setPrefix(FIS_RANK.MODERATOR.getPrefix());
        developer.setPrefix(FIS_RANK.DEVELOPER.getPrefix());
        admin.setPrefix(FIS_RANK.ADMIN.getPrefix());

        ranks.forEach((k, v) -> {
            if (ranks.get(k) == FIS_RANK.PLAYER) player.addEntity(k);
            else if (ranks.get(k) == FIS_RANK.SUPPORTER) supporter.addEntity(k);
            else if (ranks.get(k) == FIS_RANK.MODERATOR) moderator.addEntity(k);
            else if (ranks.get(k) == FIS_RANK.DEVELOPER) developer.addEntity(k);
            else if (ranks.get(k) == FIS_RANK.ADMIN) admin.addEntity(k);
        });
        Bukkit.getOnlinePlayers().forEach(p -> p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()));
        Bukkit.getOnlinePlayers().forEach(p -> p.setScoreboard(sb));
    }
}
