package io.github.scaredsmods.potion_totems.pack;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.resource.ResourcePackLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractPack {

    /**
     * The namespace used for the assets/data folders inside the pack (usually your mod id) <br>
     */
    private final String namespace;
    /**
     * {@link String} name - The name of the pack that gets generated <br>
     */
    private final String name;
    /**
     * {@link String} description - A description of the pack, that describes the pack<br>
     */
    private final String description;
    /**
     * {@link PackFormat.IntermediaryFormat} packFormat - The format this pack was developed for. Example: For 26.1.2, this would be 84.0. Where 84 is the major version and 0 is the minor version. <br>
     */
    private final PackFormat.IntermediaryFormat packFormat;
    /**
     * The folder of this pack: parentDirectory/name <br>
     */
    private final Path path;
    /**
     * {@link Gson} GSON - Writes json objects to json files <br>
     */
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    /**
     * @param parentDirectory The directory the pack folder is created in
     *                        (the resourcepacks directory, or a world's datapacks directory).
     */
    public AbstractPack(@NotNull String namespace, @NotNull String name, @NotNull String description,
                           @NotNull PackFormat.IntermediaryFormat packFormat, Path parentDirectory) {
        this.namespace = namespace;
        this.name = name;
        this.description = description;
        this.packFormat = packFormat;
        if (packFormat.min().isEmpty() || packFormat.max().isEmpty()) {
            throw new IllegalArgumentException("Pack '" + name + "' needs both a min and a max pack format");
        }
        this.path = parentDirectory.resolve(name);
    }

    public Path getPath() {
        return this.path;
    }

    public String getNamespace() {
        return this.namespace;
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

    /**
     * <p>A method that writes <b>all</b> data to disk.</p>
     * <p>Since that data can be different, this method is meant to be implemented by the extending class.</p>
     * <p>Example data would be {@link AbstractPack#writeMetadata()} <b>and must be included, otherwise Minecraft will not recognise the pack!</b></p>
     * @throws IOException
     */
    public abstract void write() throws IOException;

    /**
     * Creates the pack folder and calls {@link AbstractPack#writeMetadata()}.
     * @throws IOException
     */
    public void createPackStructure() throws IOException {
        Files.createDirectories(this.path);
        writeMetadata();
    }

    /**
     * <p>This method is used for writing the pack.mcmeta file characterising the pack.</p>
     * <p>This doesn't really need to be changed, as the format is pretty much the same for basic resource and datapacks, it doesn't need to be implemented.</p>
     * <p><b>This method must be called in {@link AbstractPack#write()}, otherwise Minecraft will not recognise the pack!</b></p>
     * @throws IOException
     */
    private void writeMetadata() throws IOException {
        JsonObject root = new JsonObject();
        JsonObject pack = new JsonObject();

        var min = getPackFormat().min().orElseThrow();
        JsonArray minArray = new JsonArray();
        minArray.add(min.major());
        minArray.add(min.minor());

        var max = getPackFormat().max().orElseThrow();
        JsonArray maxArray = new JsonArray();
        maxArray.add(max.major());
        maxArray.add(max.minor());

        root.add("pack", pack);
        pack.add("min_format", minArray);
        pack.add("max_format", maxArray);
        pack.addProperty("description", getDescription());
        writeJson(this.getPath().resolve("pack.mcmeta"), root);
    }

    /**
     * An optional method that does not need to be called, but exists to give authors an option to customise their pack.
     * By default, it will get the logoFile of the given mod_id, however you can override this method if you want to specify a different location.
     * @param modId The mod_id to grab the logoFile from
     */
    public void writePackIcon(String modId) throws IOException {
        var mod = ModList.get().getMods().stream()
                .filter(info -> info.getModId().equals(modId))
                .findFirst();
        if (mod.isEmpty()) return;

        Optional<String> logoFile = mod.get().getLogoFile();
        if (logoFile.isEmpty()) return;

        Pack.ResourcesSupplier supplier = ResourcePackLoader.getPackFor(modId)
                .orElseThrow(() -> new IOException("No resource pack found for mod " + modId));

        Files.createDirectories(this.getPath());
        PackLocationInfo location = new PackLocationInfo("mod/" + modId, Component.empty(), PackSource.BUILT_IN, Optional.empty());
        try (PackResources resources = supplier.openPrimary(location)) {
            IoSupplier<InputStream> logo = resources.getRootResource(logoFile.get().split("[/\\\\]"));
            if (logo == null) return;
            try (InputStream in = logo.get()) {
                Files.write(this.getPath().resolve("pack.png"), in.readAllBytes());
            }
        }
    }

    /**
     * Converts the given object and turns serialises it into a {@link JsonObject}, and writes it to disk,
     * along with its directory.
     */
    protected void writeJson(Path file, Object data) throws IOException {
        Files.createDirectories(file.getParent());
        Files.writeString(file, GSON.toJson(data));
    }

    /**
     * Reads a json file of String keys and String values
     * @return The parsed map, or {@link Optional#empty()} if the file doesn't exist or can't be parsed
     */
    public Optional<Map<String, String>> readJsonMap(Path file) {
        if (!Files.isRegularFile(file)) return Optional.empty();
        try (Reader reader = Files.newBufferedReader(file)) {
            Map<String, String> map = GSON.fromJson(reader, new TypeToken<Map<String, String>>() {}.getType());
            return Optional.ofNullable(map);
        } catch (IOException | JsonParseException e) {
            return Optional.empty();
        }
    }
}