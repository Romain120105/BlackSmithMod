package fr.shoqapik.blacksmithmod.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Collection;
import java.util.Collections;

public class BlackSmithIngredient {

    public static class ItemValue implements Ingredient.Value {
        private final ItemStack item;

        public ItemValue(ItemStack pItem) {
            this.item = pItem;
        }

        public Collection<ItemStack> getItems() {
            return Collections.singleton(this.item);
        }

        public JsonObject serialize() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Registry.ITEM.getKey(this.item.getItem()).toString());
            return jsonobject;
        }
    }

}
