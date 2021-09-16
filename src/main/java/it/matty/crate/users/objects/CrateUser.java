package it.matty.crate.users.objects;

import lombok.*;

import java.util.UUID;

@Getter @Setter
public class CrateUser {
    private final UUID uuid;
    private long delay = 0;
    private int crates;

    public CrateUser(UUID uuid, int crates) {
        this.uuid = uuid;
        this.crates = crates;
    }
}
