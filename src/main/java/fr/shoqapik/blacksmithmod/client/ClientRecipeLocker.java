package fr.shoqapik.blacksmithmod.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.shoqapik.blacksmithmod.recipe.BlackSmithRecipe;
import fr.shoqapik.blacksmithmod.recipe.RecipeManager;
import net.minecraft.FileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClientRecipeLocker {
    private static ClientRecipeLocker instance;

    private Minecraft minecraft = Minecraft.getInstance();
    private Gson gson = new GsonBuilder().setLenient().create();
    private ClientRecipeObject recipeObject = null;
    private File recipeFile;

    public ClientRecipeLocker(){
        this.recipeFile = findRecipeFile();
        instance = this;
        try {
            if (!recipeFile.exists()) {
                if (!recipeFile.getParentFile().exists()) {
                    recipeFile.getParentFile().mkdirs();
                }
                recipeFile.createNewFile();
                this.recipeObject = new ClientRecipeObject();
                save();
            }
            recipeObject = gson.fromJson(FileUtils.readFileToString(recipeFile, StandardCharsets.UTF_8), ClientRecipeObject.class);
        }catch (IOException exception){
            exception.printStackTrace();;
        }
    }

    private File findRecipeFile(){
        String path =  "config/blacksmith/";
        if(minecraft.isLocalServer()){
            path += minecraft.getSingleplayerServer().getWorldData().getLevelName().replaceAll(" ", "");
        }else{
            path += minecraft.getCurrentServer().ip;
        }
        path += "-"+minecraft.player.getStringUUID()+".json";
        return new File(path);
    }

    public boolean discoverItem(ItemStack stack){
        boolean res = recipeObject.discoverItem(stack);
        checkRecipes();
        save();
        return res;
    }

    public void unlockRecipe(String recipe){
        recipeObject.unlockRecipe(recipe);
    }

    private void checkRecipes(){
        for(BlackSmithRecipe recipe : RecipeManager.getRecipes()){
            boolean cancel = false;
            for(String craftedItem : recipe.getRequiredItems().keySet()){
                if(!this.recipeObject.hasItem(craftedItem)){
                    cancel = true;
                }
            }
            if(!cancel){
                unlockRecipe(recipe.getCraftedItem());
            }
        }
    }

    public boolean hasRecipe(String craftedItem) {
        return this.recipeObject.hasRecipe(craftedItem);
    }

    public void save(){
        try {
            FileUtils.write(recipeFile, gson.toJson(recipeObject));
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public static ClientRecipeLocker get() {
        return instance;
    }

    public class ClientRecipeObject {
        private List<String> discoveredItems = new ArrayList<>();
        private List<String> unlockedRecipes = new ArrayList<>();

        public boolean discoverItem(ItemStack stack){
            String key = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
            if(discoveredItems.contains(key)) return false;
            discoveredItems.add(key);
            return true;
        }

        public void unlockRecipe(String recipe){
            if(!unlockedRecipes.contains(recipe)) {
                unlockedRecipes.add(recipe);
            }
        }

        public boolean hasItem(String item){
            return discoveredItems.contains(item);
        }

        public boolean hasRecipe(String recipe) {
            return unlockedRecipes.contains(recipe);
        }
    }

}
