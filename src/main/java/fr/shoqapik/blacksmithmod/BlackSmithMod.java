package fr.shoqapik.blacksmithmod;

import fr.shoqapik.blacksmithmod.entity.BlackSmithEntity;
import fr.shoqapik.blacksmithmod.packets.ShowDialogPacket;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(BlackSmithMod.MODID)
public class BlackSmithMod {

    public static final String MODID = "blacksmith";

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<BlackSmithEntity>> BLACKSMITH_ENTITY = ENTITIES.register("blacksmith_entity",
            () -> EntityType.Builder.of(BlackSmithEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F).fireImmune().updateInterval(1).build(MODID+":blacksmith_entity"));


    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public BlackSmithMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ENTITIES.register(bus);
        INSTANCE.registerMessage(0, ShowDialogPacket.class, ShowDialogPacket::encode, ShowDialogPacket::decode, ShowDialogPacket::handle);

    }

    public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
}
