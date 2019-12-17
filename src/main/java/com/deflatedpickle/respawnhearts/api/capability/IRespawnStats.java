package com.deflatedpickle.respawnhearts.api.capability;

public interface IRespawnStats {
    IRespawnStats setHealth(float value);
    float getHealth();

    IRespawnStats setSaturation(int value);
    int getSaturation();
}
