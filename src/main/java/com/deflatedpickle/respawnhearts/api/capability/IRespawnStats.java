package com.deflatedpickle.respawnhearts.api.capability;

import org.jetbrains.annotations.NotNull;

public interface IRespawnStats {
    IRespawnStats setHealth(float value);
    float getHealth();

    IRespawnStats setSaturation(int value);
    int getSaturation();

    IRespawnStats setEffects(@NotNull int[] value);
    @NotNull int[] getEffects();

    IRespawnStats setDurations(@NotNull int[] value);
    @NotNull int[] getDurations();

    IRespawnStats setItems(@NotNull String[] value);
    @NotNull String[] getItems();
}
