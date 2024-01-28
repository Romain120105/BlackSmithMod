package fr.shoqapik.blacksmithmod.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        path += minecraft.player.getStringUUID()+".json";
        return new File(path);
    }

    public void discoverItem(ItemStack stack){
        recipeObject.discoverItem(stack);
        save();
    }

    public void unlockRecipe(String recipe){
        recipeObject.unlockRecipe(recipe);
        save();
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

        public void discoverItem(ItemStack stack){
            discoveredItems.add(ForgeRegistries.ITEMS.getDelegate(stack.getItem()).toString());
        }

        public void unlockRecipe(String recipe){
            unlockedRecipes.add(recipe);
        }

    }

}
