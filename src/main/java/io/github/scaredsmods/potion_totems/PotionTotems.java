package io.github.scaredsmods.potion_totems;

import io.github.scaredsmods.potion_totems.init.*;
import io.github.scaredsmods.potion_totems.pack.Resourcepack;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@Mod(PotionTotems.MOD_ID)
public class PotionTotems {

    public static final String MOD_ID = "potion_totems";
    public static final Logger LOGGER = LoggerFactory.getLogger("PotionTotems");
    public static final Resourcepack GENERATED_PACK = new Resourcepack("PotionTotemsExtraAssets", "Adds more assets to PotionTotems.", 34);

    public PotionTotems(IEventBus bus, ModContainer container) throws IOException {
        ModItems.PLACEHOLDER_ITEMS.init();
        ModItems.ITEMS.init();
        ModItems.TABS.init();
        ModBlocks.BLOCKS.init();
        ModBlockEntities.TYPES.init();
        ModMenuTypes.MENUS.init();
        ModPotions.POTIONS.init();
        ModVillagers.VILLAGER_PROFESSIONS.init();
        ModVillagers.POI_TYPES.init();
        ModConfigs.init();
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }


}
