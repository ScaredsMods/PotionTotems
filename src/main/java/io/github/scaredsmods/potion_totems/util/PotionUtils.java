/*
	Copyright (C) 2025 ScaredRabbitNL

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program. If not, see <https://www.gnu.org/licenses/>.
*/
package io.github.scaredsmods.potion_totems.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;

import javax.annotation.Nonnull;
import java.util.*;

public class PotionUtils {

	public static void copyContents(ItemStack source, ItemStack target) {
		if (source.has(DataComponents.POTION_CONTENTS)) {
			PotionContents contents = source.get(DataComponents.POTION_CONTENTS);
			target.set(DataComponents.POTION_CONTENTS, contents);
		}
	}

	public static <T> void copyDataComponent(@Nonnull ItemStack source, ItemStack target, DataComponentType<T> dataComponentType) {
		if (source.has(dataComponentType)) {
			target.set(dataComponentType, source.get(dataComponentType));
		}
	}


	public static void copyContents(ItemStack target, ItemStack... sources) {
		Set<Holder<MobEffect>> seenEffects = new HashSet<>();
		List<MobEffectInstance> combinedEffects = new ArrayList<>();

		for (ItemStack source : sources) {
			if (source.has(DataComponents.POTION_CONTENTS)) {
				PotionContents contents = source.get(DataComponents.POTION_CONTENTS);
				for (MobEffectInstance effect : contents.getAllEffects()) {
					if (!seenEffects.contains(effect.getEffect())) {
						seenEffects.add(effect.getEffect());
						combinedEffects.add(effect);
					}
				}
			}
		}

		if (!combinedEffects.isEmpty()) {
			PotionContents newContents = new PotionContents(
					Optional.empty(),
					Optional.empty(),
					combinedEffects
			);
			target.set(DataComponents.POTION_CONTENTS, newContents);

		}
	}
}
