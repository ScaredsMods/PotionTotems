/*
	This file is part of PotionTotems, licensed under the Lesser General Public License version 3 (LGPL-3.0)
	Copyright (C) 2025 ScaredRabbitNL

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU Lesser General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public License
	along with this program. If not, see <https://www.gnu.org/licenses/>.
*/
package io.github.scaredsmods.potion_totems.event;


import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.block.entity.AdvancedInfuserBlockEntity;
import io.github.scaredsmods.potion_totems.block.entity.InfuserBlockEntity;
import io.github.scaredsmods.potion_totems.init.ModBlockEntities;
import io.github.scaredsmods.potion_totems.init.ModBlocks;
import io.github.scaredsmods.potion_totems.init.ModConfigs;
import io.github.scaredsmods.potion_totems.init.ModMenuTypes;
import io.github.scaredsmods.potion_totems.pack.Resourcepack;
import io.github.scaredsmods.potion_totems.screen.AdvancedInfuserScreen;
import io.github.scaredsmods.potion_totems.screen.InfuserScreen;
import io.github.scaredsmods.potion_totems.tint.FromPotion;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforgespi.language.IModInfo;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = PotionTotems.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {

	}

	@SubscribeEvent
	public static void registerItemColorHandlers(RegisterColorHandlersEvent.ItemTintSources event) {
		event.register(PotionTotems.id("from_potion"), FromPotion.MAP_CODEC);
	}

	@SubscribeEvent
	public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
		event.register((state, level, pos, tintIndex) -> {
			if (tintIndex <= 0) {
				return 0xFFFFFF;
			}
			if (level == null || pos == null) {
				return 0xFFFFFF;
			}

			Optional<InfuserBlockEntity> blockEntity = level.getBlockEntity(pos, ModBlockEntities.BE_INFUSER.get());
			if (blockEntity.isEmpty()) {
				return 0xFFFFFF;
			}
			ItemStack involvedStack = null;
			ItemStack potionStack = blockEntity.get().itemStackHandler.getStackInSlot(InfuserBlockEntity.POTION_INPUT_SLOT);
			ItemStack infusedTotemStack = blockEntity.get().itemStackHandler.getStackInSlot(InfuserBlockEntity.INFUSED_TOTEM_OUTPUT_SLOT);

			if (!potionStack.isEmpty() && potionStack.has(DataComponents.POTION_CONTENTS)) {
				involvedStack = potionStack;
			} else if (!infusedTotemStack.isEmpty() && infusedTotemStack.has(DataComponents.POTION_CONTENTS)) {
				involvedStack = infusedTotemStack;
			} else {
				return 0xFFFFFF;
			}
			PotionContents contents = involvedStack.get(DataComponents.POTION_CONTENTS);

			if (contents == null) {
				return 0xFFFFFF;
			}
			return ARGB.opaque(involvedStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
		}, ModBlocks.INFUSER.get());

		event.register((state, level, pos, tintIndex) -> {
			if (tintIndex <= 0) {
				return 0xFFFFFF;
			}
			if (level == null || pos == null) {
				return 0xFFFFFF;
			}
			Optional<AdvancedInfuserBlockEntity> blockEntity = level.getBlockEntity(pos, ModBlockEntities.BE_ADVANCED_INFUSER.get());
			if (blockEntity.isEmpty()) {
				return 0xFFFFFF;
			}
			ItemStack involvedStack = null;
			ItemStack potionStack = blockEntity.get().stackHandler.getStackInSlot(AdvancedInfuserBlockEntity.POTION_INPUT_SLOT);
			ItemStack infusedTotemStack = blockEntity.get().stackHandler.getStackInSlot(AdvancedInfuserBlockEntity.INFUSED_TOTEM_OUTPUT_SLOT);

			if (!potionStack.isEmpty() && potionStack.has(DataComponents.POTION_CONTENTS)) {
				involvedStack = potionStack;
			} else if (!infusedTotemStack.isEmpty() && infusedTotemStack.has(DataComponents.POTION_CONTENTS)) {
				involvedStack = infusedTotemStack;
			} else {
				return 0xFFFFFF;
			}
			PotionContents contents = involvedStack.get(DataComponents.POTION_CONTENTS);

			if (contents == null) {
				return 0xFFFFFF;
			}
			return ARGB.opaque(involvedStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
		}, ModBlocks.ADVANCED_INFUSER.get());
	}

	@SubscribeEvent
	public static void registerScreens(RegisterMenuScreensEvent event) {
		event.register(ModMenuTypes.INFUSER_MENU.get(), InfuserScreen::new);
		event.register(ModMenuTypes.ADVANCED_INFUSER_MENU.get(), AdvancedInfuserScreen::new);
	}

	/*
	TODO: Re-add BER's
	@SubscribeEvent
	public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.BE_INFUSER.get(), InfuserBER::new);
		event.registerBlockEntityRenderer(ModBlockEntities.BE_ADVANCED_INFUSER.get(), AdvancedInfuserBER::new);
	}
	 */

	@SubscribeEvent
	public static void onFMLLoadComplete(FMLLoadCompleteEvent event) throws IOException {
		Resourcepack pack = PotionTotems.GENERATED_PACK;
		BuiltInRegistries.POTION.stream().forEach(potion -> {
			List<MobEffect> effects = potion.getEffects().stream()
					.map(MobEffectInstance::getEffect)
					.map(Holder::value)
					.toList();
			for (MobEffect effect : effects) {
				Identifier id = BuiltInRegistries.MOB_EFFECT.getKey(effect);
				String formattedName = Arrays.stream(id.getPath().split("_"))
						.map(word -> word.substring(0,1).toUpperCase() + word.substring(1))
						.collect(Collectors.joining(" "));
				String translationKey = "item.potion_totems.infused_totem.effect." + id.getPath();

				if (!id.getNamespace().equals("minecraft") && !id.getNamespace().equals(PotionTotems.MOD_ID)) {
					pack.getTranslationModule().addTranslation(translationKey, "Infused Totem of " + formattedName);
				}
			}
		});

		Set<String> previousModIdsSet = new HashSet<>(ModConfigs.devConfig.devSection.loadedModIds.get());
		Set<String> currentModIds = ModList.get().getMods().stream()
				.map(IModInfo::getModId)
				.filter(modId -> !modId.equals("java"))
				.collect(Collectors.toSet());
		Set<String> newMods = new HashSet<>(currentModIds);
		newMods.removeAll(previousModIdsSet);
		boolean hasNewMods = !newMods.isEmpty();
		pack.write();
		pack.writePackIcon(PotionTotems.MOD_ID);
		if(hasNewMods) {
			pack.apply();
			PotionTotems.LOGGER.info("New mods were found! To be sure that no values are missed, {} will now re-apply!", pack.getName());
			ModConfigs.devConfig.devSection.loadedModIds.validateAndSet(new ArrayList<>(currentModIds));
			ModConfigs.devConfig.save();
		} else {
			PotionTotems.LOGGER.info("No new mods found! {} will not re-apply!", pack.getName());
		}
	}
}
