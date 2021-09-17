package it.matty.crate.users.objects;

import lombok.*;

import java.util.UUID;

@Getter @Setter
public class CrateUser {
    private final UUID uuid;
    private long delay = System.currentTimeMillis();
    private int crates;
    private boolean isOpening;

    public CrateUser(UUID uuid, int crates) {
        this.uuid = uuid;
        this.crates = crates;
    }
}
