package com.deflatedpickle.respawnhearts.common.config;

import com.deflatedpickle.respawnhearts.Reference;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "respawnhearts")
@Config(modid = "respawnhearts", name = "respawnhearts")
@Config.LangKey("config.respawnhearts.general")
public class GeneralConfig {
    @Config.Name("Health")
    @Config.Comment("The default health to respawn with")
    @Config.LangKey("config.respawnhearts.defaultHealth")
    public static float defaultHealth = 10f;

    @Config.Name("Saturation")
    @Config.Comment("The default saturation to respawn with")
    @Config.LangKey("config.respawnhearts.defaultSaturation")
    public static int defaultSaturation = 10;

    @Config.Name("Effects")
    public static int[] defaultPotionEffects = new int[]{};

    @Config.Name("Durations")
    public static int[] defaultPotionDurations = new int[]{};

    @Config.Name("Items")
    public static String[] defaultItemList = new String[]{};

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
