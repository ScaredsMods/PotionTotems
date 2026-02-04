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
package io.github.scaredsmods.potion_totems.mixin.client;



import io.github.scaredsmods.potion_totems.item.TotemItem;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
	@Inject(at = @At("HEAD"), method = "findTotem", cancellable = true)
	private static void findTotem(Player player, CallbackInfoReturnable<ItemStack> cbi)
	{
		for (InteractionHand interactionHand : InteractionHand.values()) {
			ItemStack itemStack = player.getItemInHand(interactionHand);
			if (itemStack.getItem() instanceof TotemItem)
				cbi.setReturnValue(itemStack);
		}
	}
}
