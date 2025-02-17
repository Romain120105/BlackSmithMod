package fr.shoqapik.btemobs.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import java.util.Collection;
import java.util.stream.Stream;

public class BteIngredientSerializer implements IIngredientSerializer<Ingredient> {

    public static final BteIngredientSerializer INSTANCE  = new BteIngredientSerializer();

    @Override
    public Ingredient parse(FriendlyByteBuf buffer) {
        return Ingredient.fromValues(Stream.generate(() -> new BteIngredientSerializer.ItemValue(buffer.readItem())).limit(buffer.readVarInt()));
    }

    @Override
    public Ingredient parse(JsonObject json) {
        return Ingredient.fromValues(Stream.of(getValuesFromJson(json)));
    }

    @Override
    public void write(FriendlyByteBuf buffer, Ingredient ingredient) {
        ItemStack[] items = ingredient.getItems();
        buffer.writeVarInt(items.length);

        for (ItemStack stack : items)
            buffer.writeItem(stack);
    }

    public static Ingredient.Value getValuesFromJson(JsonObject json) {
        if (json.has("item") && json.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
        } else if (json.has("item")) {
            Item item = ShapedRecipe.itemFromJson(json);
            return new BteIngredientSerializer.ItemValue(new ItemStack(item, json.get("count").getAsInt()));
        } else if (json.has("tag")) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
            TagKey<Item> tagkey = TagKey.create(Registry.ITEM_REGISTRY, resourcelocation);
            return new BteIngredientSerializer.TagValue(tagkey, json.get("count").getAsInt());
        } else {
            throw new JsonParseException("An ingredient entry needs either a tag or an item");
        }
    }

    public static class ItemValue extends Ingredient.ItemValue {

        public ItemValue(ItemStack pItem) {
            super(pItem);
        }

        @Override
        public Collection<ItemStack> getItems() {
            return super.getItems();
        }

        @Override
        public JsonObject serialize() {
            JsonObject jsonObject = super.serialize();
            ItemStack item = this.getItems().iterator().next();
            if(item.getCount() != 1) {
                jsonObject.addProperty("count", item.getCount());
            }

            return jsonObject;
        }
    }

    public static class TagValue extends Ingredient.TagValue {

        private final int count;

        public TagValue(TagKey<Item> pTag, int count) {
            super(pTag);
            this.count = count;
        }

        @Override
        public Collection<ItemStack> getItems() {
            return super.getItems().stream().map(itemStack -> {
                itemStack.setCount(count);
                return itemStack;
            }).toList();
        }

        @Override
        public JsonObject serialize() {
            JsonObject jsonObject = super.serialize();
            jsonObject.addProperty("count", count);
            return jsonObject;
        }
    }
}
