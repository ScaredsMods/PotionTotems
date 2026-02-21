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
package io.github.scaredsmods.potion_totems.init;

import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;


public class ModPotions {

	public static final ResourcefulRegistry<Potion> POTIONS = ResourcefulRegistries.create(BuiltInRegistries.POTION, PotionTotems.MOD_ID);

	public static final HolderRegistryEntry<Potion> AGGRESSION = POTIONS.registerHolder("aggression", () -> new Potion("aggressive",new MobEffectInstance(MobEffects.STRENGTH, 6000, 1),
			new MobEffectInstance(MobEffects.REGENERATION, 6000, 1), new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 1),
			new MobEffectInstance(MobEffects.INFESTED, 6000)
	));

	public static final HolderRegistryEntry<Potion> POSITIVE = POTIONS.registerHolder("positive", () -> new Potion("positive", addEffects(MobEffectCategory.BENEFICIAL)));
	public static final HolderRegistryEntry<Potion> NEGATIVE = POTIONS.registerHolder("negative", () -> new Potion("negative", addEffects(MobEffectCategory.HARMFUL)));
	public static final HolderRegistryEntry<Potion> NEUTRAL = POTIONS.registerHolder("neutral", () -> new Potion("neutral", addEffects(MobEffectCategory.NEUTRAL)));

	private static MobEffectInstance[] addEffects(MobEffectCategory category) {
		return BuiltInRegistries.MOB_EFFECT.stream()
				.filter(effect -> effect.getCategory() == category)
				.map(Holder::direct)
				.map(holder -> new MobEffectInstance(holder, 12000,1))
				.toArray(MobEffectInstance[]::new);
	}

	private static MobEffectInstance[] addEffects(MobEffectCategory category, int duration) {
		return BuiltInRegistries.MOB_EFFECT.stream()
				.filter(effect -> effect.getCategory() == category)
				.map(Holder::direct)
				.map(holder -> new MobEffectInstance(holder, duration,1))
				.toArray(MobEffectInstance[]::new);
	}

	private static MobEffectInstance[] addEffects(MobEffectCategory category, int duration, int amplifier) {
		return BuiltInRegistries.MOB_EFFECT.stream()
				.filter(effect -> effect.getCategory() == category)
				.map(Holder::direct)
				.map(holder -> new MobEffectInstance(holder, duration,amplifier))
				.toArray(MobEffectInstance[]::new);
	}

}
