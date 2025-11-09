package io.github.scaredsmods.examplemod;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;



@Mod(ExampleMod.MOD_ID)
public class ExampleMod {

    public static final String MOD_ID = "example_mod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod(IEventBus bus, ModContainer container) {

    }
}
