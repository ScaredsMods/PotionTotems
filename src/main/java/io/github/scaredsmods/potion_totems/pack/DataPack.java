package io.github.scaredsmods.potion_totems.pack;

import java.io.IOException;

public class DataPack extends AbstractPack {

    public DataPack(String name, String description, int[] packFormat, int[] minPackFormat, int[] maxPackFormat) {
        super(name, description, PackType.DATA_PACK, packFormat, minPackFormat, maxPackFormat);
    }

    @Override
    public void write() throws IOException {

    }


    @Override
    public void writePackIcon(String modId) {

    }
}
