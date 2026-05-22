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
package io.github.scaredsmods.potion_totems.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeModeTab;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.item.InfusedTotemItem;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {
	public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, PotionTotems.MOD_ID);
	public static final ResourcefulRegistry<CreativeModeTab> TABS = ResourcefulRegistries.create(BuiltInRegistries.CREATIVE_MODE_TAB, PotionTotems.MOD_ID);

	public static final HolderRegistryEntry<Item> INFUSED_TOTEM = registerItem("infused_totem", (properties) ->
			new InfusedTotemItem(properties.rarity(Rarity.RARE).stacksTo(1).component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)));
	public static final HolderRegistryEntry<Item> INFUSER_CORE = registerItem("infuser_core", Item::new);

	public static final HolderRegistryEntry<Item> INFUSER_TOTEM_PH_1 = registerItem("infuser_totem_placeholder_1", Item::new);
	public static final HolderRegistryEntry<Item> INFUSER_TOTEM_PH_2 = registerItem("infuser_totem_placeholder_2", Item::new);

	public static final RegistryEntry<CreativeModeTab> TOTEMS = TABS.register("totems", () -> new ResourcefulCreativeModeTab(PotionTotems.id("totems"))
			.setItemIcon(() -> Items.TOTEM_OF_UNDYING)
			.addContent(() -> ITEMS.boundStream()
					.filter(item -> item != INFUSER_TOTEM_PH_1.get() && item != INFUSER_TOTEM_PH_2.get())
					.map(ItemStack::new))
			.addContent(() -> BuiltInRegistries.POTION.stream()
					.map(Holder::direct)
					.filter(holder -> BuiltInRegistries.POTION.getKey(holder.value()).getNamespace().equals(PotionTotems.MOD_ID))
					.map(holder -> {
						ItemStack stack = new ItemStack(Items.POTION);
						stack.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
						return stack;
					}))
			.addContent(() -> BuiltInRegistries.POTION.stream()
					.map(Holder::direct)
					.filter(holder -> BuiltInRegistries.POTION.getKey(holder.value()).getNamespace().equals(PotionTotems.MOD_ID))
					.map(holder -> {
						ItemStack stack = new ItemStack(Items.SPLASH_POTION);
						stack.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
						return stack;
					}))
			.addContent(() -> BuiltInRegistries.POTION.stream()
					.map(Holder::direct)
					.filter(holder -> BuiltInRegistries.POTION.getKey(holder.value()).getNamespace().equals(PotionTotems.MOD_ID))
					.map(holder -> {
						ItemStack stack = new ItemStack(Items.LINGERING_POTION);
						stack.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
						return stack;
					}))
			.build());

	private static HolderRegistryEntry<Item> registerItem(String name, Function<Item.Properties, Item> block) {
		ResourceKey<Item> key = PotionTotems.resourceKey(Registries.ITEM, name);
		Supplier<Item.Properties> supplier = Item.Properties::new;
		return ITEMS.registerHolder(name, () -> block.apply(supplier.get().useItemDescriptionPrefix().setId(key)));
	}


}
