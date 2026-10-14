package io.github.scaredsmods.potion_totems.pack;

import java.io.IOException;

public class DataPack extends AbstractPack {

    public DataPack(String name, String description, double packFormat, double minPackFormat, double maxPackFormat) {
        super(name, description, PackType.DATA_PACK, packFormat, minPackFormat, maxPackFormat);
    }

    @Override
    public void write() throws IOException {

    }


    @Override
    public void writePackIcon(String modId) {

    }
}
