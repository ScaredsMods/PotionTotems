package io.github.scaredsmods.potion_totems.event;

import com.teamresourceful.resourcefullib.ResourcefulLib;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.pack.ResourcepackBuilder;
import me.fzzyhmstrs.fzzy_config.FC;
import me.fzzyhmstrs.fzzy_config.FzzyConfigNeoForge;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.ModSorter;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = PotionTotems.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

    private static final List<String> modIds = List.of("minecraft", PotionTotems.MOD_ID, ResourcefulLib.MOD_ID, "fzzy_config", "kotlinforforge", "neoforge");

    @SubscribeEvent
    public static void onFMLLoadComplete(FMLLoadCompleteEvent event) throws IOException {
        ResourcepackBuilder builder = ResourcepackBuilder.create("PotionTotemsExtraAssetsTest", "", 34);
        BuiltInRegistries.POTION.stream().forEach(potion -> {
            List<MobEffect> effects = potion.getEffects().stream()
                    .map(MobEffectInstance::getEffect)
                    .map(Holder::value)
                    .toList();

            for (MobEffect effect : effects) {
                ResourceLocation loc = BuiltInRegistries.MOB_EFFECT.getKey(effect);
                String formattedName = Arrays.stream(loc.getPath().split("_"))
                        .map(word -> word.substring(0,1).toUpperCase() + word.substring(1))
                        .collect(Collectors.joining(" "));
                String translationKey = "item.potion_totems.infused_totem.effect." + loc.getPath();

                if (!loc.getNamespace().equals("minecraft") && !loc.getNamespace().equals(PotionTotems.MOD_ID)) {
                    builder.addTranslation(translationKey, "Infused Totem of " + formattedName);
                }
            }
        });
        ModList.get().getMods().forEach(mod -> {
                if (!modIds.contains(mod.getModId())) {
                    try {
                        builder.generate();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    builder.applyPack();
                }
        });
    }
}