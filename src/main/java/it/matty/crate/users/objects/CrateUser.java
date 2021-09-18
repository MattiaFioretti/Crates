package it.matty.crate.users.objects;

public interface CrateUser {
    java.util.UUID getUuid();

    long getDelay();

    void setDelay(long delay);

    int getCrates();

    void setCrates(int crates);

    boolean isOpening();

    void setOpening(boolean isOpening);
}
