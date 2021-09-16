package it.matty.crate.users;

import it.matty.crate.users.objects.CrateUser;
import org.bukkit.entity.Player;

import java.util.Set;

public interface IUserManager {

    Set<CrateUser> getUsers();

    CrateUser getUser(Player player);

    void addUser(CrateUser user);

    boolean canOpen(Player player);

    boolean isDelay(Player player);

    void addCrate(Player player);

    void removeCrate(Player player);

    int getCrates(Player player);
}
