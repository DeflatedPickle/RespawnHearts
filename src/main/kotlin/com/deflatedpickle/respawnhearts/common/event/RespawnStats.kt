/* Copyright (c) 2019 DeflatedPickle under the MIT license */

package com.deflatedpickle.respawnhearts.common.event

import com.deflatedpickle.respawnhearts.Reference
import com.deflatedpickle.respawnhearts.api.capability.IRespawnStats
import com.deflatedpickle.respawnhearts.common.config.GeneralConfig
import java.util.concurrent.Callable
import net.minecraft.entity.EntityLivingBase
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.nbt.NBTTagString
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.ICapabilitySerializable

object RespawnStats {
    val NAME = ResourceLocation(Reference.MOD_ID, "respawn_stats")

    fun isCapable(entity: EntityLivingBase): IRespawnStats? = entity.getCapability(Provider.CAPABILITY, null)

    class Implementation : IRespawnStats {
        private var health = GeneralConfig.defaultHealth
        private var saturation = GeneralConfig.defaultSaturation
        private var effectList = GeneralConfig.defaultPotionEffects
        private var durationList = GeneralConfig.defaultPotionDurations
        private var itemList = GeneralConfig.defaultItemList

        override fun setHealth(value: Float): IRespawnStats {
            this.health = value

            return this
        }

        override fun getHealth(): Float = this.health

        override fun setSaturation(value: Int): IRespawnStats {
            this.saturation = value

            return this
        }

        override fun getSaturation(): Int = this.saturation

        override fun setEffects(value: IntArray): IRespawnStats {
            this.effectList = value

            return this
        }

        override fun getEffects(): IntArray = this.effectList

        override fun setDurations(value: IntArray): IRespawnStats {
            this.durationList = value

            return this
        }

        override fun getDurations(): IntArray = this.durationList

        override fun setItems(value: Array<String>): IRespawnStats {
            this.itemList = value

            return this
        }

        override fun getItems(): Array<String> = this.itemList
    }

    class Storage : Capability.IStorage<IRespawnStats> {
        override fun readNBT(capability: Capability<IRespawnStats>?, instance: IRespawnStats?, side: EnumFacing?, nbt: NBTBase?) {
            if (instance is Implementation) {
                with(nbt as NBTTagCompound) {
                    instance.health = this.getFloat("health")
                    instance.saturation = this.getInteger("saturation")
                    instance.effects = this.getIntArray("effects")
                    instance.durations = this.getIntArray("times")
                    this.getTagList("items", 9).apply {
                        val list = arrayOf<String>()
                        for (i in 0 until this.tagCount()) {
                            list[i] = this.getStringTagAt(i)
                        }
                        instance.items = list
                    }
                }
            }
        }

        override fun writeNBT(capability: Capability<IRespawnStats>?, instance: IRespawnStats?, side: EnumFacing?): NBTBase? {
            instance?.let {
                return NBTTagCompound().apply {
                    setFloat("health", instance.health)
                    setInteger("saturation", instance.saturation)
                    setIntArray("effects", instance.effects)
                    setIntArray("times", instance.durations)
                    setTag("items", NBTTagList().apply {
                        for (i in 0 until instance.items.count()) {
                            this.set(i, NBTTagString(instance.items[i]))
                        }
                    })
                }
            }
            return null
        }
    }

    class Factory : Callable<IRespawnStats> {
        override fun call(): IRespawnStats = Implementation()
    }

    class Provider : ICapabilitySerializable<NBTBase> {
        companion object {
            @JvmStatic
            @CapabilityInject(IRespawnStats::class)
            lateinit var CAPABILITY: Capability<IRespawnStats>
        }

        val INSTANCE = CAPABILITY.defaultInstance

        override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean = capability == CAPABILITY
        override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?): T? = if (capability == CAPABILITY) CAPABILITY.cast(this.INSTANCE) else null

        override fun serializeNBT(): NBTBase = CAPABILITY.storage.writeNBT(CAPABILITY, this.INSTANCE, null)!!
        override fun deserializeNBT(nbt: NBTBase) = CAPABILITY.storage.readNBT(CAPABILITY, this.INSTANCE, null, nbt)
    }

    fun register() {
        CapabilityManager.INSTANCE.register(IRespawnStats::class.java, Storage(), Factory())
    }
}
