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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.resource.ResourcePackLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ResourcePack extends AbstractPack {

	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Map<String, String> translationMap = new HashMap<>();

	public ResourcePack(String name, String description, int[] packFormat, int[] minPackFormat, int[] maxPackFormat) {
		super(name, description, PackType.RESOURCE_PACK, packFormat, minPackFormat, maxPackFormat);
	}

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
