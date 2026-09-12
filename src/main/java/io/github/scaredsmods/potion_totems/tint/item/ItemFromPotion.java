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
package io.github.scaredsmods.potion_totems.tint.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.Nullable;

public record ItemFromPotion(int defaultColor) implements ItemTintSource {

	public static final MapCodec<ItemFromPotion> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
					ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(ItemFromPotion::defaultColor)
			).apply(instance, ItemFromPotion::new)
	);

	@Override
	public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
		PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
		return contents != null ? ARGB.opaque(contents.getColor()) : this.defaultColor();
	}

	@Override
	public MapCodec<ItemFromPotion> type() {
		return MAP_CODEC;
	}
}
