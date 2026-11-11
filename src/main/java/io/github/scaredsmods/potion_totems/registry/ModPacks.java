package io.github.scaredsmods.potion_totems.registry;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.pack.data.DataPack;
import io.github.scaredsmods.potion_totems.pack.resource.ResourcePack;
import net.minecraft.server.packs.metadata.pack.PackFormat;

import java.nio.file.Path;
import java.util.Optional;

public class ModPacks {

    private static final PackFormat.IntermediaryFormat RESOURCE_FORMAT = new PackFormat.IntermediaryFormat(
            Optional.of(PackFormat.of(84)), Optional.of(PackFormat.of(84)), Optional.empty(), Optional.empty());

    // TODO: verify the data pack format for 26.1.2, it differs from the resource pack format.
    private static final PackFormat.IntermediaryFormat DATA_FORMAT = new PackFormat.IntermediaryFormat(
            Optional.of(PackFormat.of(84)), Optional.of(PackFormat.of(84)), Optional.empty(), Optional.empty());

    private static ResourcePack generatedResources;

    private ModPacks() {}

    /**
     * Creates the resource pack and returns it. Call this from client code,
     * with Minecraft.getInstance().getResourcePackDirectory().
     */
    public static ResourcePack initResources(Path resourcePackDirectory) {
        generatedResources = new ResourcePack(PotionTotems.MOD_ID, "PotionTotemsExtraAssets",
                "Adds more assets to PotionTotems.", RESOURCE_FORMAT, resourcePackDirectory);
        return generatedResources;
    }

    public static ResourcePack resources() {
        if (generatedResources == null) {
            throw new IllegalStateException("ModPacks.initResources() has not been called yet");
        }
        return generatedResources;
    }

    /** Creates a data pack for a world, e.g. with server.getWorldPath(LevelResource.DATAPACK_DIR). */
    public static DataPack createData(Path datapackDirectory) {
        return new DataPack(PotionTotems.MOD_ID, "PotionTotemsExtraData",
                "Adds more data to PotionTotems", DATA_FORMAT, datapackDirectory);
    }
}