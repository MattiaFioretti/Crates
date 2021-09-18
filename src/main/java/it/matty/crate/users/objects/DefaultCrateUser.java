package it.matty.crate.users.objects;

import lombok.Data;

import java.util.UUID;

@Data
public class DefaultCrateUser implements CrateUser {
    private final UUID uuid;
    private long delay = System.currentTimeMillis();
    private int crates = 0;
    private boolean isOpening = false;
}
