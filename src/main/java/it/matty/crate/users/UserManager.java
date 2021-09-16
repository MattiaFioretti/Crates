package it.matty.crate.users;

import it.matty.crate.CratePlugin;
import it.matty.crate.users.objects.CrateUser;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class UserManager implements IUserManager {
    @Getter Set<CrateUser> users = new HashSet<>();

    @Getter private final int CRATE_PER_DAY = CratePlugin.getPlugin().getConfig().getInt("settings.crate-per-day");
    @Getter private final int CRATE_DELAY = CratePlugin.getPlugin().getConfig().getInt("settings.crate-delay");

    @Override
    public CrateUser getUser(Player player) {
        for(CrateUser user : users) {
            if(user.getUuid().equals(player.getUniqueId()))
                return user;
        }
        return null;
    }

    @Override
    public boolean canOpen(Player player) {
        if(player.hasPermission("crates.bypass")) return false;
        return getUser(player).getCrates() < CRATE_PER_DAY;
    }

    @Override
    public boolean isDelay(Player player) {
        if(player.hasPermission("crates.bypass")) return false;
        return (System.currentTimeMillis() - getUser(player).getDelay()) >= (CRATE_DELAY * 1000L);
    }

    @Override
    public void addCrate(Player player) {
        getUser(player).setCrates(getCrates(player) + 1);
    }

    @Override
    public void removeCrate(Player player) {
        getUser(player).setCrates(getCrates(player) - 1);
    }

    @Override
    public int getCrates(Player player) {
        return getUser(player).getCrates();
    }

    @Override
    public void addUser(CrateUser user) {
        users.add(user);
    }
}
