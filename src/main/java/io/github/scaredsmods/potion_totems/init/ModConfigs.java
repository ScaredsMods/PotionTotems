package io.github.scaredsmods.potion_totems.init;

import io.github.scaredsmods.potion_totems.config.ModCommonConfig;
import io.github.scaredsmods.potion_totems.config.ModDevConfig;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;

public class ModConfigs {


    public static ModCommonConfig commonConfig = ConfigApiJava.registerAndLoadConfig(ModCommonConfig::new, RegisterType.BOTH);
    public static ModDevConfig devConfig = ConfigApiJava.registerAndLoadConfig(ModDevConfig::new, RegisterType.BOTH);

    public static void init() {}
}
