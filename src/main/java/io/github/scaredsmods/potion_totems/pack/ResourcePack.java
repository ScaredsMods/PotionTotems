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
package io.github.scaredsmods.potion_totems.pack;

import com.google.gson.JsonObject;
import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.item.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class ResourcePack extends AbstractPack {

    /**
     * Key-Value map of String and String, symbolising the translation key and the readable representation.
     */
    private static final Map<String, String> translationMap = new HashMap<>();

	public ResourcePack(String name, String description, PackFormat.IntermediaryFormat packFormat) {
		super(name, description, PackType.RESOURCE_PACK, packFormat);
	}

    /**
     * An example implementation of {@link AbstractPack#write()}, used for resourcepacks.
     * In this case, only translations get written to disk, since that is all this mod needs.
     * @throws IOException
     */

	@Override
	public void write() throws IOException {
		if (!Files.exists(this.getPath())) {
			Files.createDirectory(this.getPath());
		}
		writeMetadata();
		JsonObject json = new JsonObject();

		for (Map.Entry<String, String> entry : translationMap.entrySet()) {
			Path filePath = this.getPath().resolve("assets/" + PotionTotems.MOD_ID + "/lang/en_us.json");
			Files.createDirectories(filePath.getParent());
			json.addProperty(entry.getKey(), entry.getValue());
			Files.writeString(filePath, GSON.toJson(json), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		}
	}

    public TranslationModule getTranslationModule() {
		return new TranslationModule();
    }

    /**
     * A method that must be called if you want to actually use the resources generated.
     * It works purely client-side, as it should, and adds your pack to the pack repository, ready to be loaded.
     * It can be loaded anytime a client-side event happens, like pressing a key.
     */
	public void apply() {
		Minecraft mc = Minecraft.getInstance();
		PackRepository packRepository = mc.getResourcePackRepository();
		packRepository.reload();
		packRepository.addPack("file/" + getName());
		packRepository.reload();
		mc.reloadResourcePacks();
	}


	public static class TranslationModule {

		public void addItemTranslation(Item keyItem, String translation) {
			translationMap.put(keyItem.getDescriptionId(), translation);
		}
		public void addTranslation(String name, String translation) {
			translationMap.put(name, translation);
		}
	}
}
