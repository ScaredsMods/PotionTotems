package io.github.scaredsmods.potion_totems.data.gen;


import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = PotionTotems.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper exFileHelper = e.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = e.getLookupProvider();

        generator.addProvider(e.includeServer(), new ModRecipesProvider(packOutput, provider));
        generator.addProvider(e.includeClient(), new ModItemModelProvider(packOutput, exFileHelper));
        generator.addProvider(true, new ModEnglishLanguageProvider(packOutput));
    }

}
