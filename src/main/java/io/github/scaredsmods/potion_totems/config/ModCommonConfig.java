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
package io.github.scaredsmods.potion_totems.config;

import io.github.scaredsmods.potion_totems.PotionTotems;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.api.FileType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber;
import org.jetbrains.annotations.NotNull;


@Version(version = 1)
public class ModCommonConfig extends Config {

	public ModCommonConfig() {
		super(PotionTotems.id("common"));
	}

	public InfusedTotemSection infusedTotemSection = new InfusedTotemSection();
	public static class InfusedTotemSection extends ConfigSection {

		public InfusedTotemSection() {
			super();
		}

		public ValidatedInt duration = new ValidatedInt(400, 5000, 20, ValidatedNumber.WidgetType.SLIDER);
		public ValidatedInt amplifier = new ValidatedInt(0, 4, 0, ValidatedNumber.WidgetType.SLIDER);
	}


	@Override
	public @NotNull FileType fileType() {
		return FileType.JSON5;
	}
}
