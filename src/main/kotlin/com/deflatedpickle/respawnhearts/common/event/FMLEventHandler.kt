/* Copyright (c) 2019 DeflatedPickle under the MIT license */

package com.deflatedpickle.respawnhearts.common.event

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent

class FMLEventHandler {
    @SubscribeEvent
    fun onAttachCapabilitiesEventEntity(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is EntityPlayer) {
            event.addCapability(RespawnStats.NAME, RespawnStats.Provider())
        }
    }

    @SubscribeEvent
    fun onPlayerRespawnEvent(event: PlayerEvent.PlayerRespawnEvent) {
        // Fired only on the server
        RespawnStats.isCapable(event.player)?.let {
            event.player.health = it.health
            event.player.foodStats.foodLevel = it.saturation
        }
    }
}
