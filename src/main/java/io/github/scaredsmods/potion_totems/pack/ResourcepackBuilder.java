package io.github.scaredsmods.potion_totems.pack;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;


public class ResourcepackBuilder {

    private final Path path;
    private final String name;
    private final String description;
    private final int packFormat;
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private ResourcepackBuilder(String name, String description, int packFormat) {
        this.name = name;
        this.path = Paths.get("resourcepacks/" + this.name);
        this.description = name + " - " + description;
        this.packFormat = packFormat;
    }

    public static ResourcepackBuilder create(String name, String description, int packFormat) throws IOException {
        return new ResourcepackBuilder(name, description, packFormat);
    }

    public void generate() throws IOException {
        if (!Files.exists(this.path)) {
            Files.createDirectory(this.path);
        }
        generateMetaData();
        JsonObject json = new JsonObject();

        for (Map.Entry<String, String> entry : translationMap.entrySet()) {
            Path filePath = this.path.resolve("assets/" + PotionTotems.MOD_ID + "/lang/en_us.json");
            Files.createDirectories(filePath.getParent());
            json.addProperty(entry.getKey(), entry.getValue());
            Files.writeString(filePath, GSON.toJson(json), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    private void generateMetaData() throws IOException {
        JsonObject root = new JsonObject();
        JsonObject pack = new JsonObject();

        root.add("pack", pack);
        pack.addProperty("pack_format", this.packFormat);
        pack.addProperty("description", this.description);
        Files.writeString(this.path.resolve("pack.mcmeta"), GSON.toJson(root), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private final Map<String, String> translationMap = new HashMap<>();

    public void addItemTranslation(Item keyItem, String translation) {
        this.translationMap.put(keyItem.getDescriptionId(), translation);
    }
    public void addTranslation(String name, String translation) {
        this.translationMap.put(name, translation);
    }

    public Path getPath() {
        return this.path;
    }

    public void applyPack() {
        Minecraft mc = Minecraft.getInstance();
        mc.getResourcePackRepository().reload();
        mc.getResourcePackRepository().addPack("file/" + this.name);
        mc.getResourcePackRepository().reload();
        mc.reloadResourcePacks();
    }
}





