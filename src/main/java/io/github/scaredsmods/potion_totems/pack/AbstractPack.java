package io.github.scaredsmods.potion_totems.pack;

import com.google.gson.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
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

    private final String name;
    private final String description;
    private final PackType type;
    private final int[] packFormat;
    private final int[] minPackFormat;
    private final int[] maxPackFormat;
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public AbstractPack(String name, String description, PackType type, int packFormat[], int[] minPackFormat, int[] maxPackFormat) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.packFormat = packFormat;
        this.minPackFormat = minPackFormat;
        this.maxPackFormat = maxPackFormat;
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
    public int[] getPackFormat() {
        return this.packFormat;
    }

    public int[] getMinPackFormat() {
        return this.minPackFormat;
    }

    public int[] getMaxPackFormat() {
        return this.maxPackFormat;
    }

    public abstract void write() throws IOException;

    public void writeMetadata() throws IOException {
        JsonObject root = new JsonObject();
        JsonObject pack = new JsonObject();

        JsonArray format = new JsonArray();
        Arrays.stream(getPackFormat()).asDoubleStream().forEach(format::add);
        JsonArray minFormat = new JsonArray();
        Arrays.stream(getMinPackFormat()).asDoubleStream().forEach(minFormat::add);
        JsonArray maxFormat = new JsonArray();
        Arrays.stream(getMaxPackFormat()).asDoubleStream().forEach(maxFormat::add);

        root.add("pack", pack);
        pack.add("pack_format", format);
        pack.add("min_format", minFormat);
        pack.add("max_format", maxFormat);
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
