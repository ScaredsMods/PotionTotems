package io.github.scaredsmods.potion_totems.pack.data;

import io.github.scaredsmods.potion_totems.pack.AbstractPack;
import net.minecraft.server.packs.metadata.pack.PackFormat;

import java.io.IOException;
import java.nio.file.Path;

public class DataPack extends AbstractPack {

    public DataPack(String namespace, String name, String description,
                    PackFormat.IntermediaryFormat packFormat, Path datapackDirectory) {
        super(namespace, name, description, packFormat, datapackDirectory);
    }

    @Override
    public void write() throws IOException {
        createPackStructure();
    }
}