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

import io.github.scaredsmods.potion_totems.config.ModCommonConfig;
import io.github.scaredsmods.potion_totems.config.ModDevConfig;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;

public class ModConfigs {


	public static ModCommonConfig commonConfig = ConfigApiJava.registerAndLoadConfig(ModCommonConfig::new, RegisterType.BOTH);
	public static ModDevConfig devConfig = ConfigApiJava.registerAndLoadConfig(ModDevConfig::new, RegisterType.BOTH);

	public static void init() {}
}
