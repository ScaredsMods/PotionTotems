package io.github.scaredsmods.potion_totems.pack.resource;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.ItemLike;

import java.util.Map;
import java.util.TreeMap;

public class TranslationHolder {

    private final String locale;
    /**
     * Key-Value map of String and String, symbolising the translation key and the readable representation.
     */
    private final Map<String, String> entries = new TreeMap<>();

    public TranslationHolder(String locale) {
        this.locale = locale;
    }

    public String locale() {
        return this.locale;
    }

    public Map<String, String> entries() {
        return this.entries;
    }

    /**
     * Adds a key-value pair to the translation map based off just an {@link ItemLike}.
     * @param keyItem The item-like object to generate the translation for (key)
     * @param translation The readable representation of the key (value)
     */
    public TranslationHolder addItemTranslation(ItemLike keyItem, String translation) {
        this.entries.put(keyItem.asItem().getDescriptionId(), translation);
        return this;
    }

    /**
     * Adds a key-value pair to the translation map based off just a {@link MobEffectInstance}
     * @param instance The {@link MobEffectInstance} to translate (key)
     * @param translation The in-game name of the {@link MobEffectInstance} (value)
     */
    public TranslationHolder addMobEffectInstanceTranslation(MobEffectInstance instance, String translation) {
        this.entries.put(instance.getDescriptionId(), translation);
        return this;
    }

    /**
     * Adds a key-value pair to the translation map based off a string
     * @param key The string key to add a translation for (key)
     * @param translation The readable representation of the key (value)
     */
    public TranslationHolder addTranslation(String key, String translation) {
        this.entries.put(key, translation);
        return this;
    }
}