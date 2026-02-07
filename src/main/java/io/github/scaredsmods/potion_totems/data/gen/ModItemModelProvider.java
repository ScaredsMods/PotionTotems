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
import io.github.scaredsmods.potion_totems.init.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, PotionTotems.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		basicItemWithParent(ModItems.INFUSED_TOTEM.get(), PotionTotems.id("item/totem_base"));
		basicItem(ModItems.INFUSER_TOTEM_PH_1.get());
		basicItem(ModItems.INFUSER_TOTEM_PH_2.get());
	}

	public ItemModelBuilder basicItemWithParent(Item item, ResourceLocation parentId) {
		ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
		String itemName = itemId.getPath();
		return withExistingParent(itemName, parentId);
	}
}
