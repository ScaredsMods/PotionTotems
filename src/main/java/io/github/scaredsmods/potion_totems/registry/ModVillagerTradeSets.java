package io.github.scaredsmods.potion_totems.registry;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.TradeSets;

import static net.minecraft.world.item.trading.TradeSets.WEAPONSMITH_LEVEL_5;

public class ModVillagerTradeSets {


    public static void bootstrap(BootstrapContext<TradeSet> context) {
        TradeSets.register(context, WEAPONSMITH_LEVEL_5, VillagerTradeTags.WEAPONSMITH_LEVEL_5);
    }
}

