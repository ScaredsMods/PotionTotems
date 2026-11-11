package io.github.scaredsmods.potion_totems.pack.resource;

import io.github.scaredsmods.potion_totems.pack.AbstractPack;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.repository.PackRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ResourcePack extends AbstractPack {

	/**
	 * One {@link TranslationHolder} per locale, e.g. "en_us".
	 */
	private final Map<String, TranslationHolder> translationHolders = new LinkedHashMap<>();

	public ResourcePack(String namespace, String name, String description,
                        PackFormat.IntermediaryFormat packFormat, Path resourcePackDirectory) {
		super(namespace, name, description, packFormat, resourcePackDirectory);
	}

	/**
	 * Gets (or creates) the {@link TranslationHolder} for a locale,
	 * e.g. {@code translations("en_us").addItemTranslation(item, "Name")}.
	 */
	public TranslationHolder translations(String locale) {
		return this.translationHolders.computeIfAbsent(locale, TranslationHolder::new);
	}

	/**
	 * An example implementation of {@link AbstractPack#write()}, used for resourcepacks.
	 * In this case, only translations get written to disk, since that is all this mod needs.
	 * @throws IOException
	 */
	@Override
	public void write() throws 	IOException {
		createPackStructure();

		for (TranslationHolder holder : this.translationHolders.values()) {
			Path langFile = getPath()
					.resolve("assets")
					.resolve(getNamespace())
					.resolve("lang")
					.resolve(holder.locale() + ".json");
			writeJson(langFile, holder.entries());
		}
	}

	private Path langFile(TranslationHolder holder) {
		return getPath()
				.resolve("assets")
				.resolve(getNamespace())
				.resolve("lang")
				.resolve(holder.locale() + ".json");
	}

	/**
	 * Compares the translations in memory with the lang files currently on disk.
	 * Must be called <b>before</b> {@link ResourcePack#write()}, since write() overwrites the files.
	 * @return true if the pack doesn't exist yet, or if any locale differs from what is on disk
	 */
	public boolean compareDiskBytes() {
		if (!Files.isRegularFile(getPath().resolve("pack.mcmeta"))) return true;

		for (TranslationHolder holder : this.translationHolders.values()) {
			Optional<Map<String, String>> onDisk = readJsonMap(langFile(holder));
			if (onDisk.isEmpty() || !onDisk.get().equals(holder.entries())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A method that must be called if you actually want to use the resources generated.
	 * It works purely client-side, as it should, and adds your pack to the pack repository, ready to be loaded.
	 * It can be loaded anytime a client-side event happens, like pressing a key.
	 */
	public void apply() {
		Minecraft mc = Minecraft.getInstance();
		PackRepository packRepository = mc.getResourcePackRepository();
		packRepository.reload();
		packRepository.addPack("file/" + this.getName());
		packRepository.reload();
		mc.reloadResourcePacks();
	}
}