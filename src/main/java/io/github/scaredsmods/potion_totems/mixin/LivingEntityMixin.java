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
package io.github.scaredsmods.potion_totems.mixin;


import io.github.scaredsmods.potion_totems.item.TotemItem;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{
	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Shadow
	public abstract ItemStack getItemInHand(InteractionHand interactionHand);

	@Inject(at = @At("HEAD"), method = "checkTotemDeathProtection", cancellable = true)
	private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cbi)
	{
		if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
		{
			for(InteractionHand hand : InteractionHand.values())
			{
				ItemStack stack = this.getItemInHand(hand);
				if (stack.getItem() instanceof TotemItem && ((TotemItem) stack.getItem()).canActivate((LivingEntity)(Object)this)) {
					ItemStack activateStack = stack.copy();
					stack.shrink(1);

					((TotemItem)activateStack.getItem()).activateTotem((LivingEntity)(Object)this, activateStack);

					cbi.setReturnValue(true);
				}
			}

		}
	}
}
