/* Copyright (c) 2019 DeflatedPickle under the MIT license */

package com.deflatedpickle.respawnhearts

import com.deflatedpickle.respawnhearts.common.event.FMLEventHandler
import com.deflatedpickle.respawnhearts.common.event.RespawnStats
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS,
        dependencies = Reference.DEPENDENCIES,
        modLanguageAdapter = Reference.ADAPTER
)
object RespawnHearts {
    val logger: Logger = LogManager.getLogger(Reference.MOD_ID)

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger.info("Registering capability")
        RespawnStats.register()
        logger.info("Capability registered")
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        logger.info("Registering event handler")
        MinecraftForge.EVENT_BUS.register(FMLEventHandler())
        logger.info("Event handler registered")
    }
}
