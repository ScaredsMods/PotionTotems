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
package io.github.scaredsmods.potion_totems.item;


import io.github.scaredsmods.potion_totems.config.ModCommonConfig;
import io.github.scaredsmods.potion_totems.init.ModConfigs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class InfusedTotemItem extends Item implements TotemItem {

	public InfusedTotemItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getDefaultInstance() {
		ItemStack stack = super.getDefaultInstance();
		stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
		return stack;
	}

	@Override
	public boolean canActivate(LivingEntity entity) {
		return true;
	}

	@Override
	public void activateTotem(LivingEntity entity, ItemStack stack) {
		if (stack != null) {
			if (entity instanceof ServerPlayer) {
				((ServerPlayer) entity).awardStat(Stats.ITEM_USED.get(this));
				CriteriaTriggers.USED_TOTEM.trigger(((ServerPlayer) entity), stack);
			}
			entity.setHealth(entity.getMaxHealth() / 2F);
			entity.removeAllEffects();
			addEffects(entity, stack);
			addVanillaEffects(entity);
			entity.level().broadcastEntityEvent(entity, (byte)35);
			stack.shrink(1);
		}
	}

	private void addVanillaEffects(LivingEntity entity) {
		entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
		entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800));
	}

	@Override
	public Component getName(ItemStack stack) {
		PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
		if (contents == null || contents.potion().isEmpty()) {
			return Component.translatable(this.getDescriptionId() + ".effect.empty");
		}
		String potionName = contents.potion().get().value().name();
		return Component.translatable(this.getDescriptionId() + ".effect." + potionName);
	}

	private void addEffects(LivingEntity entity, ItemStack stack) {
		PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
		ModCommonConfig.InfusedTotemSection infusedTotemSection = ModConfigs.commonConfig.infusedTotemSection;
		Collection<MobEffectInstance> vanillaEffects = List.of(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1), new MobEffectInstance(MobEffects.REGENERATION, 900, 1), new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800));
		if (contents != null) {
			if (contents.hasEffects() && !(new HashSet<>(contents.customEffects()).containsAll(vanillaEffects))) {
				contents.forEachEffect(instance -> entity.addEffect(new MobEffectInstance(instance.getEffect(),infusedTotemSection.duration.get(), infusedTotemSection.amplifier.get())), 1);
			}
		}
	}
}
