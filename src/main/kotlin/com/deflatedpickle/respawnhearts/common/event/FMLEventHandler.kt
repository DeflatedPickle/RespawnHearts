/* Copyright (c) 2019 DeflatedPickle under the MIT license */

package com.deflatedpickle.respawnhearts.common.event

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
import net.minecraftforge.fml.common.registry.ForgeRegistries

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

            for ((i, potion) in it.effects.withIndex()) {
                Potion.getPotionById(potion)?.let { id ->
                    event.player.addPotionEffect(PotionEffect(id, it.durations[i]))
                }
            }

            for (i in it.items) {
                ForgeRegistries.ITEMS.getValue(ResourceLocation(i))?.let { item ->
                    event.player.inventory.addItemStackToInventory(ItemStack(item))
                }
            }
        }
    }
}
