package fr.shoqapik.blacksmithmod;

import fr.shoqapik.blacksmithmod.menu.SmithCraftMenu;
import fr.shoqapik.blacksmithmod.menu.SmithCraftProvider;
import fr.shoqapik.blacksmithmod.entity.BlackSmithEntity;
import fr.shoqapik.blacksmithmod.packets.ActionPacket;
import fr.shoqapik.blacksmithmod.packets.PlaceRecipePacket;
import fr.shoqapik.blacksmithmod.packets.ShowDialogPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod(BlackSmithMod.MODID)
public class BlackSmithMod {

    public static final String MODID = "blacksmith";

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES,
            MODID);
    public static final RegistryObject<EntityType<BlackSmithEntity>> BLACKSMITH_ENTITY = ENTITIES.register("blacksmith_entity",
            () -> EntityType.Builder.of(BlackSmithEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F).fireImmune().updateInterval(1).build(MODID+":blacksmith_entity"));

    public static final RegistryObject<MenuType<SmithCraftMenu>> SMITH_CRAFT_MENU = CONTAINERS
            .register("smith_craft", () -> new MenuType<>(new MenuType.MenuSupplier<SmithCraftMenu>() {
                @Override
                public SmithCraftMenu create(int p_39995_, Inventory p_39996_) {
                    return new SmithCraftMenu(p_39995_, p_39996_);
                }
            }));

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
        CONTAINERS.register(bus);
        INSTANCE.registerMessage(0, ShowDialogPacket.class, ShowDialogPacket::encode, ShowDialogPacket::decode, ShowDialogPacket::handle);
        INSTANCE.registerMessage(1, ActionPacket.class, ActionPacket::encode, ActionPacket::decode, ActionPacket::handle);
        INSTANCE.registerMessage(2, PlaceRecipePacket.class, PlaceRecipePacket::encode, PlaceRecipePacket::decode, PlaceRecipePacket::handle);

    }

    public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static void handleActionPacket(ActionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if(msg.actionType.equals("open_craft")){
            NetworkHooks.openScreen(ctx.get().getSender(), new SmithCraftProvider());
        }
    }

    public static void handlePlaceRecipePacket(PlaceRecipePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getSender().containerMenu instanceof SmithCraftMenu){
            SmithCraftMenu menu = (SmithCraftMenu) ctx.get().getSender().containerMenu;
            menu.placeRecipe(ctx.get().getSender(), msg.recipe);
        }
    }
}
