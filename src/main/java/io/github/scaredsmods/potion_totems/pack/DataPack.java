package io.github.scaredsmods.potion_totems.pack;

import net.minecraft.server.packs.metadata.pack.PackFormat;

import java.io.IOException;

public class DataPack extends AbstractPack {

    public DataPack(String name, String description, PackFormat.IntermediaryFormat packFormat) {
        super(name, description, PackType.DATA_PACK, packFormat);
    }

    @Override
    public void write() throws IOException {

    }


    @Override
    public void writePackIcon(String modId) {

    }
}
