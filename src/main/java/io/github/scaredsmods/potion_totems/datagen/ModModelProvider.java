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
package io.github.scaredsmods.potion_totems.datagen;

import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.component.property.TotemFragmentProperty;
import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import io.github.scaredsmods.potion_totems.registry.ModItems;
import io.github.scaredsmods.potion_totems.tint.item.ItemFromPotion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.numeric.CustomModelDataProperty;
import net.minecraft.client.resources.model.cuboid.ItemModelGenerator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public class ModModelProvider extends ModelProvider {

	public ModModelProvider(PackOutput output ) {
		super(output, PotionTotems.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		itemModels.generateLayeredItem(ModItems.INFUSED_TOTEM.get(), new Material(PotionTotems.id("item/totem_base")), new Material(PotionTotems.id("item/totem_eye")));
		itemModels.generateFlatItem(ModItems.INFUSER_TOTEM_PH_1.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ModItems.INFUSER_TOTEM_PH_2.get(), ModelTemplates.FLAT_ITEM);
		itemModels.itemModelOutput.accept(
				ModItems.INFUSED_TOTEM.get(),
				new CuboidItemModelWrapper.Unbaked(
						ModelLocationUtils.getModelLocation(ModItems.INFUSED_TOTEM.get()),
						Optional.empty(),
						List.of(new ItemFromPotion(0x000000))
				)
		);
		itemModels.generateFlatItem(ModItems.MACHINE_CORE.get(), ModelTemplates.FLAT_ITEM);

		Item totemFragment = ModItems.TOTEM_FRAGMENT.get();
		Item infusedTotemFragment = ModItems.INFUSED_TOTEM_FRAGMENT.get();
		List<ItemModel.Unbaked> fragmentModels = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			fragmentModels.add(
					ItemModelUtils.plainModel(
							itemModels.createFlatItemModel(totemFragment, "_" + i, ModelTemplates.FLAT_ITEM)
					)
			);
		}

		itemModels.itemModelOutput.accept(
				totemFragment,
				ItemModelUtils.rangeSelect(
						new TotemFragmentProperty(),
						1.0F,
						fragmentModels.get(0),
						ItemModelUtils.override(fragmentModels.get(1), 2.0F),
						ItemModelUtils.override(fragmentModels.get(2), 3.0F),
						ItemModelUtils.override(fragmentModels.get(3), 4.0F),
						ItemModelUtils.override(fragmentModels.get(4), 5.0F),
						ItemModelUtils.override(fragmentModels.get(5), 6.0F),
						ItemModelUtils.override(fragmentModels.get(6), 7.0F),
						ItemModelUtils.override(fragmentModels.get(7), 8.0F),
						ItemModelUtils.override(fragmentModels.get(8), 9.0F)
				),
				new ClientItem.Properties(true, false, 1.0F)
		);

		List<ItemModel.Unbaked> infusedFragmentModels = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			Material fragmentTexture = TextureMapping.getItemTexture(totemFragment, "_" + i);
			Identifier flatModel = createFlatItemModel(infusedTotemFragment, "_" + i, fragmentTexture, itemModels);

			infusedFragmentModels.add(
					new CuboidItemModelWrapper.Unbaked(
							flatModel,
							Optional.empty(),
							List.of(new ItemFromPotion(0x000000))
					)
			);
		}

		itemModels.itemModelOutput.accept(
				infusedTotemFragment,
				ItemModelUtils.rangeSelect(
						new TotemFragmentProperty(),
						1.0F,
						infusedFragmentModels.get(0),
						ItemModelUtils.override(infusedFragmentModels.get(1), 2.0F),
						ItemModelUtils.override(infusedFragmentModels.get(2), 3.0F),
						ItemModelUtils.override(infusedFragmentModels.get(3), 4.0F),
						ItemModelUtils.override(infusedFragmentModels.get(4), 5.0F),
						ItemModelUtils.override(infusedFragmentModels.get(5), 6.0F),
						ItemModelUtils.override(infusedFragmentModels.get(6), 7.0F),
						ItemModelUtils.override(infusedFragmentModels.get(7), 8.0F),
						ItemModelUtils.override(infusedFragmentModels.get(8), 9.0F)
				),
				new ClientItem.Properties(true, false, 1.0F));

	}

	@Override
	protected Stream<? extends Holder<Block>> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream()
				.filter(e -> e instanceof HolderRegistryEntry<Block>)
				.map(e -> ((HolderRegistryEntry<Block>) e).holder())
				.filter(h -> !h.is(ModBlocks.INFUSER.holder()) && !h.is(ModBlocks.ADVANCED_INFUSER.holder()) && !h.is(ModBlocks.INFUSER_FRAME.holder()) && !h.is(ModBlocks.CRUSHER.holder()));
	}

	@Override
	protected Stream<? extends Holder<Item>> getKnownItems() {
		return ModItems.ITEMS.getEntries().stream()
				.filter(e -> e instanceof HolderRegistryEntry<Item>)
				.map(e -> ((HolderRegistryEntry<Item>) e).holder())
				.filter(h -> !h.is(PotionTotems.id("infuser")) && !h.is(PotionTotems.id("advanced_infuser")) && !h.is(PotionTotems.id("infuser_frame")) && !h.is(PotionTotems.id("crusher")));

	}

	public Identifier createFlatItemModel(Item item, String suffix, Material texture, ItemModelGenerators generators) {
		Identifier location = ModelLocationUtils.getModelLocation(item, suffix);
		return ModelTemplates.FLAT_ITEM.create(location, TextureMapping.layer0(texture), generators.modelOutput);
	}
}
