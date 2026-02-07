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
package io.github.scaredsmods.potion_totems.config;

import io.github.scaredsmods.potion_totems.PotionTotems;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.annotations.WithPerms;
import me.fzzyhmstrs.fzzy_config.api.FileType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Version(version = 1)
public class ModDevConfig extends Config {
	public ModDevConfig() {
		super(PotionTotems.id("dev"));
	}

	@WithPerms(opLevel = 10)
	@Comment("This is a dev config AND NOT SUPPOSED TO BE EDITED! PROCEED WITH CAUTION! \nOnly edit this if you know what you are doing!")
	public DevSection devSection = new DevSection();

	public static class DevSection extends ConfigSection {
		public DevSection() {
			super();
		}

		@Comment("The mod ids that are used for reapplying builtin resourcepack!. Don't edit this!")
		public ValidatedList<String> loadedModIds = new ValidatedList<>(
				new ArrayList<>(),
				new ValidatedString());
	}

	@Override
	public @NotNull FileType fileType() {
		return FileType.JSON5;
	}
}
