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
package io.github.scaredsmods.potion_totems.event;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.init.ModPotions;
import io.github.scaredsmods.potion_totems.init.ModVillagers;
import io.github.scaredsmods.potion_totems.util.PotionType;
import io.github.scaredsmods.potion_totems.util.VillagerUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;



@EventBusSubscriber(modid = PotionTotems.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModGameEvents {

	private static final List<Holder<Potion>> regularPotionsTier1 = List.of(Potions.INFESTED, Potions.OOZING, Potions.LUCK, Potions.LEAPING, Potions.HEALING);
	private static final List<Holder<Potion>> regularPotionsTier2 = List.of(Potions.NIGHT_VISION, Potions.STRONG_REGENERATION, Potions.POISON);
	private static final List<Holder<Potion>> regularPotionsTier3 = List.of(ModPotions.AGGRESSION.holder(), Potions.STRONG_STRENGTH, Potions.STRONG_POISON);
	private static final List<Holder<Potion>> potionTotemsTier1 = List.of(Potions.INVISIBILITY, Potions.LEAPING, Potions.FIRE_RESISTANCE);
	private static final List<Holder<Potion>> potionTotemsTier2 = List.of(Potions.HEALING, Potions.REGENERATION, Potions.STRENGTH);
	private static final List<Holder<Potion>> potionTotemsTier3 = List.of(ModPotions.AGGRESSION.holder());

	@SubscribeEvent
	public static void addCustomTrades(VillagerTradesEvent event) {
		if(event.getType() == ModVillagers.TOTEM_MASTER.get()) {
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

			int emeraldCost = 32;
			int maxUses = 15;
			float priceMultiplier = 0.05F;

			VillagerUtils.makeTrade(trades, regularPotionsTier1, PotionType.POTION, 1, 1, priceMultiplier,  maxUses, emeraldCost);
			VillagerUtils.makeTrade(trades, potionTotemsTier1, PotionType.POTION_TOTEM, 1,  0, maxUses - 10, 3, priceMultiplier * 1.5F, 0, emeraldCost);
			VillagerUtils.makeTrade(trades, regularPotionsTier2, PotionType.POTION, 2, 2, priceMultiplier * 1.05F, maxUses - 5, emeraldCost + (emeraldCost / 2));
			VillagerUtils.makeTrade(trades, potionTotemsTier2, PotionType.POTION, 2, 2, priceMultiplier * 1.05F, maxUses - 5, emeraldCost + (emeraldCost / 2));
			VillagerUtils.makeTrade(trades, regularPotionsTier3, PotionType.POTION, 3, 3, priceMultiplier * 1.5F, maxUses - 10, emeraldCost * 2);
			VillagerUtils.makeTrade(trades, potionTotemsTier3, PotionType.POTION_TOTEM, 3, 3, priceMultiplier * 1.5F, maxUses - 10, emeraldCost * 2);


		}
	}

	@SubscribeEvent
	public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addMix(Potions.WATER, Items.NETHERITE_INGOT, ModPotions.POSITIVE.holder());
		event.getBuilder().addMix(Potions.WATER, Items.NETHER_STAR, ModPotions.NEGATIVE.holder());
		event.getBuilder().addMix(Potions.WATER, Items.DRAGON_HEAD, ModPotions.NEUTRAL.holder());
	}

}
