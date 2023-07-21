package de.verfxgbar.utils;

public enum FIS_RANK {
    NONE("","","none"),
    PLAYER("§7Spieler ׀ ", "", "player"),
    SUPPORTER("§9Sup §7 ׀ ", "", "supporter"),
    MODERATOR("§cMod §7׀ ", "", "moderator"),
    DEVELOPER("§dDev §7׀ ", "", "developer"),
    ADMIN("§4Admin §7׀ ", "", "admin");
    private String prefix, suffix, cons;


    FIS_RANK(String prefix, String suffix, String cons) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.cons = cons;
    }

    String getPrefix() {
        return prefix;
    }

    String getSuffix() {
        return suffix;
    }

    @Override
    public String toString() {
    return cons;
    }
}
