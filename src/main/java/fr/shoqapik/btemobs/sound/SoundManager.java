package fr.shoqapik.btemobs.sound;


import fr.shoqapik.btemobs.BteMobsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.intellij.lang.annotations.Identifier;

import java.rmi.registry.Registry;


public class SoundManager {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BteMobsMod.MODID);

    // Registra el sonido con el ResourceLocation correcto
    public static final RegistryObject<SoundEvent> HAMMER = SOUND_EVENTS.register("hammer",
            () -> new SoundEvent(new ResourceLocation(BteMobsMod.MODID, "hammer")));

    private SoundManager(){

    }
}

