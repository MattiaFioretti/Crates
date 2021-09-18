package it.matty.crate.users.objects;

public interface CrateUser {
    java.util.UUID getUuid();

    long getDelay();

    int getCrates();

    boolean isOpening();

    void setDelay(long delay);

    void setCrates(int crates);

    void setOpening(boolean isOpening);
}
