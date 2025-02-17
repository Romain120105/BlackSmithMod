package fr.shoqapik.btemobs.client;

import fr.shoqapik.btemobs.BteMobsMod;
import fr.shoqapik.btemobs.recipe.api.BteAbstractRecipe;
import fr.shoqapik.btemobs.recipe.api.RecipeCategory;
import fr.shoqapik.btemobs.registry.BteMobsRecipeTypes;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = BteMobsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SuppressWarnings("all")
    @SubscribeEvent
    public static void onRegisterRecipeBookCategory(RegisterRecipeBookCategoriesEvent event) {
        RecipeBookType recipeBookType = RecipeBookType.create("BLACKSMITH");

        List<RecipeBookCategories> list = new ArrayList<>();
        for(RecipeCategory category : RecipeCategory.values()) {
            list.add(RecipeBookCategories.create("BLACKSMITH_" + category.name(), category.item));
        }
        event.registerBookCategories(recipeBookType, list);

        List<RecipeBookCategories> list1 = new ArrayList<>(list);
        list1.add(RecipeBookCategories.SMITHING);
        list1.remove(RecipeCategory.ALL.getVanillaCategory());
        event.registerAggregateCategory(RecipeCategory.ALL.getVanillaCategory(), list1);

        event.registerRecipeCategoryFinder(BteMobsRecipeTypes.BLACKSMITH_RECIPE.get(), (recipe) -> {
            if(recipe instanceof BteAbstractRecipe) {
                return RecipeBookCategories.valueOf("BLACKSMITH_" + ((BteAbstractRecipe)recipe).getCategory().name());
            }
            return null;
        });

        event.registerRecipeCategoryFinder(BteMobsRecipeTypes.BLACKSMITH_UPGRADE_RECIPE.get(), (recipe) -> {
            if(recipe instanceof BteAbstractRecipe) {
                return RecipeBookCategories.valueOf("BLACKSMITH_" + ((BteAbstractRecipe)recipe).getCategory().name());
            }
            return null;
        });
    }

}
