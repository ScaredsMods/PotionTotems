package io.github.scaredsmods.potion_totems.pack;

import com.google.gson.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.util.GsonHelper;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.resource.ResourcePackLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Optional;

public abstract class AbstractPack {

    /**
     * {@link String} name - The name of the pack that get's generated <br>
     * {@link String} description - A description of the pack, that shortly describes what the pack does <br>
     * {@link PackType} packType - What type of pack this is: Resource or datapack <br>
     * {@link PackFormat.IntermediaryFormat} packFormat - The format this pack was developed for. Example: For 26.1.2, this would be 84.0. Where 84 is the major version and 0 is the minor version. <br>
     * {@link Gson} GSON - Writes json objects to json files <br>
     */
    private final String name;
    private final String description;
    private final PackType type;
    private final PackFormat.IntermediaryFormat packFormat;
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public AbstractPack(String name, String description, PackType type, PackFormat.IntermediaryFormat packFormat) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.packFormat = packFormat;
    }

    public Path getPath() {
        return Paths.get(this.type.folderName + "/" + this.name);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
    public PackFormat.IntermediaryFormat getPackFormat() {
        return this.packFormat;
    }

    public abstract void write() throws IOException;

    public void writeMetadata() throws IOException {
        JsonObject root = new JsonObject();
        JsonObject pack = new JsonObject();

        JsonArray minArray =  new JsonArray();
        if (getPackFormat().min().isEmpty()) return;
        minArray.add(getPackFormat().min().get().major());
        minArray.add(getPackFormat().min().get().minor());

        JsonArray maxArray =  new JsonArray();
        if (getPackFormat().max().isEmpty()) return;
        maxArray.add(getPackFormat().max().get().major());
        maxArray.add(getPackFormat().max().get().minor());

        root.add("pack", pack);
        pack.add("min_format", minArray);
        pack.add("max_format", maxArray);
        pack.addProperty("description", getDescription());
        Files.writeString(this.getPath().resolve("pack.mcmeta"), GSON.toJson(root), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void writePackIcon(String modId) {
        Path filePath = getPath().resolve("pack.png");
        ModList.get().getMods().forEach(mod -> {
            if (mod.getModId().equals(modId)) {
                mod.getLogoFile().map(logoFile -> {
                    Pack.ResourcesSupplier resourcePack = ResourcePackLoader.getPackFor(mod.getModId()).get();
                    try (PackResources packResources = resourcePack.openPrimary(new PackLocationInfo("mod/" + mod.getModId(), Component.empty(), PackSource.BUILT_IN, Optional.empty()))) {
                        IoSupplier<InputStream> logoResource = packResources.getRootResource(logoFile.split("[/\\\\]"));
                        assert logoResource != null;
                        InputStream resources = logoResource.get();
                        Files.write(filePath, resources.readAllBytes(), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                    } catch (IOException | IllegalArgumentException e) {
                        throw new RuntimeException(e);
                    }
                    return logoFile;
                });
            }
        });
    }

    public enum PackType {
        RESOURCE_PACK("resourcepacks"),
        DATA_PACK("datapacks");

        private final String folderName;

        PackType(String folderName) {
            this.folderName = folderName;
        }
    }
}
