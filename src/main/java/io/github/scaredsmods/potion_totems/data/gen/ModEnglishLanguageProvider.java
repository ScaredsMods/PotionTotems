/*
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
package io.github.scaredsmods.potion_totems.data.gen;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.init.ModBlocks;
import io.github.scaredsmods.potion_totems.init.ModPotions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModEnglishLanguageProvider extends LanguageProvider {

	public ModEnglishLanguageProvider(PackOutput output) {
		super(output, PotionTotems.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		BuiltInRegistries.POTION.holders().forEach(holder -> {
			ResourceLocation id = BuiltInRegistries.POTION.getKey(holder.value());
			String effectName = id.getPath();

			String formattedName = Arrays.stream(effectName.split("_"))
					.map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
					.collect(Collectors.joining(" "));

			add("item.potion_totems.infused_totem.effect." + effectName, "Infused Totem of " + formattedName);
		});

		ModPotions.POTIONS.stream().forEach(potionRegistryEntry -> {
			ResourceLocation id = potionRegistryEntry.getId();
			String potionName = id.getPath();

			String formattedName = Arrays.stream(potionName.split("_"))
					.map(word -> word.substring(0,1).toUpperCase() + word.substring(1))
					.collect(Collectors.joining(" "));

			add("item.minecraft.potion.effect." + potionName, "Potion of " + formattedName);
			add("item.minecraft.splash_potion.effect." + potionName, "Splash Potion of " + formattedName);
			add("item.minecraft.lingering_potion.effect." + potionName, "Lingering Potion of " + formattedName);
		});

		add("item.potion_totems.infused_totem.effect.empty", "Infused Totem");
		add("item.potion_totems.infused_totem.effect.custom", "Infused Totem");
		add("itemGroup.potion_totems.totems", "Potion Totems");
		add(ModBlocks.INFUSER.get(), "Infuser");
		add(ModBlocks.ADVANCED_INFUSER.get(), "Advanced Infuser");
		add("potion_totems.be.infuser.name", "Totem Infuser");
		add("potion_totems.gui.infuser.title", "Totem Infuser");
		add("potion_totems.be.advanced_infuser.name", "Advanced Totem Infuser");
		add("potion_totems.gui.advanced_infuser.title", "Advanced Totem Infuser");
		add("entity.minecraft.villager.potion_totems.totem_master", "Totem Master");



	}
}
