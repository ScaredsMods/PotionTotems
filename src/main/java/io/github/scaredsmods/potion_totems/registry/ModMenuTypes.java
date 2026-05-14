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

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.screen.menu.AdvancedInfuserMenu;
import io.github.scaredsmods.potion_totems.screen.menu.InfuserMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;

public class ModMenuTypes {

	public static final ResourcefulRegistry<MenuType<?>> MENUS = ResourcefulRegistries.create(BuiltInRegistries.MENU, PotionTotems.MOD_ID);
	public static final RegistryEntry<MenuType<InfuserMenu>> INFUSER_MENU = registerMenuType("infuser_menu" , InfuserMenu::new);
	public static final RegistryEntry<MenuType<AdvancedInfuserMenu>> ADVANCED_INFUSER_MENU = registerMenuType("advanced_infuser_menu", AdvancedInfuserMenu::new);


	private static <T extends AbstractContainerMenu> RegistryEntry<MenuType<T>> registerMenuType(String  name, IContainerFactory<T> factory) {
		return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
	}
}
