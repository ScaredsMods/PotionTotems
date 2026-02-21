/*
 * MIT License
 *
 * Copyright (c) 2022 LASER KNIGHTS
 *
 * For full license text, see the LICENSE file in the project root or visit:
 * https://opensource.org/licenses/MIT
 */
package io.github.scaredsmods.potion_totems.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface TotemItem {

	boolean canActivate(LivingEntity entity);
	void activateTotem(LivingEntity entity, ItemStack stack);
}
