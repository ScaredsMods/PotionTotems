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
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Resourcepack {

	private final String name;
	private final String description;
	private final int packFormat;
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Map<String, String> translationMap = new HashMap<>();

	public Resourcepack(String name, String description, int packFormat) {
		this.name = name;
		this.description = description;
		this.packFormat = packFormat;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}
	public int getPackFormat() {
		return this.packFormat;
	}

	public void write() throws IOException {
		if (!Files.exists(this.getPath())) {
			Files.createDirectory(getPath());
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

	private void writeMetadata() throws IOException {
		JsonObject root = new JsonObject();
		JsonObject pack = new JsonObject();

		root.add("pack", pack);
		pack.addProperty("pack_format", this.packFormat);
		pack.addProperty("description", this.description);
		Files.writeString(this.getPath().resolve("pack.mcmeta"), GSON.toJson(root), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	public void writePackIcon(String modId) {
		Path filePath = getPath().resolve("pack.png");
		ModList.get().getMods().forEach(mod -> {
			if (mod.getModId().equals(modId)) {
				mod.getLogoFile().map(logoFile -> {
					Pack.ResourcesSupplier resourcePack = ResourcePackLoader.getPackFor(mod.getModId()).get();
					try (PackResources packResources = resourcePack.openPrimary(new PackLocationInfo("mod/" + mod.getModId(), Component.empty(), PackSource.BUILT_IN, Optional.empty()))) {
						IoSupplier<InputStream> logoResource = packResources.getRootResource(logoFile.split("[/\\\\]"));
						assert logoResource != null;
						InputStream resources = logoResource.get();
						Files.write(filePath, resources.readAllBytes(), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
					} catch (IOException | IllegalArgumentException e) {
						throw new RuntimeException(e);
					}
					return logoFile;
				});
			}
		});
	}

public TranslationModule getTranslationModule() {
		return new TranslationModule();
}

	public void apply() {
		Minecraft mc = Minecraft.getInstance();
		PackRepository packRepository = mc.getResourcePackRepository();
		packRepository.reload();
		packRepository.addPack("file/" + this.name);
		packRepository.reload();
		mc.reloadResourcePacks();
	}

	private Path getPath() {
		return Paths.get("resourcepacks/" + name);
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
