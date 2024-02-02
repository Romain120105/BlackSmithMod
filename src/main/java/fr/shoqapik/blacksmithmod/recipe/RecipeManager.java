package fr.shoqapik.blacksmithmod.recipe;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.quests.Quest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.*;

public class RecipeManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final List<BlackSmithRecipe> recipes = Lists.newArrayList();

    public RecipeManager() {
        super(GSON, "smith_recipes");
        List<ResourceLocation> items = ForgeRegistries.ITEMS.getKeys().stream().toList();
        /*Random random = new Random();
        for(int index = 0; index < 35; ++index) {
            String item = items.get(random.nextInt(items.size())).toString();
            BlackSmithRecipe recipe = new BlackSmithRecipe(item);
            for(int i =0; i < 2+random.nextInt(4); i++){
                recipe.getRequiredItems().put(items.get(random.nextInt(items.size())).toString(), random.nextInt(64));
            }
            recipes.add(recipe);
        }
        BlackSmithRecipe recipe = new BlackSmithRecipe("minecraft:netherite_sword");
        recipe.setCategory(RecipeCategory.WEAPONS);
        recipe.getRequiredItems().put("minecraft:netherite_ingot", 2);
        BlackSmithRecipe recipe1 = new BlackSmithRecipe("minecraft:netherite_chestplate");
        recipe1.setCategory(RecipeCategory.ARMORS);
        recipe1.getRequiredItems().put("minecraft:netherite_ingot", 2);
        recipes.add(recipe);
        recipes.add(recipe1);*/
    }

    public static BlackSmithRecipe getRecipe(String recipe) {
        for (BlackSmithRecipe entry : recipes) {
            if (entry.getCraftedItem().equals(recipe)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> p_10793_, ResourceManager p_10794_, ProfilerFiller p_10795_) {
       recipes.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : p_10793_.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            try {

                BlackSmithRecipe recipe = GSON.fromJson(entry.getValue(), BlackSmithRecipe.class);
                if (recipe == null) {
                    LOGGER.info("Skipping loading quest {} as it's serializer returned null", resourcelocation);
                    continue;
                }
                recipes.add(recipe);
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                LOGGER.error("Parsing error loading recipe {}", resourcelocation, jsonparseexception);
            }
        }
    }

    public static List<BlackSmithRecipe> getRecipes() {
        return recipes;
    }

    public static List<BlackSmithRecipe> getRecipesFor(RecipeCategory category){
        List<BlackSmithRecipe> result = new ArrayList<>();
        for (BlackSmithRecipe recipe : getRecipes()) {
            if(category == RecipeCategory.ALL || recipe.getCategory() == category){
                result.add(recipe);
            }
        }
        return result;
    }

    public static boolean isRecipeItem(ItemStack stack) {
        String key = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
        for(BlackSmithRecipe recipe : recipes){
            if(recipe.getRequiredItems().containsKey(key)){
                return true;
            }
        }
        return false;

    }
}
