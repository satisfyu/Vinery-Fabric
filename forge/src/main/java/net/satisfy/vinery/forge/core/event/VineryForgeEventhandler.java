package net.satisfy.vinery.forge.core.event;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.forge.core.config.VineryForgeConfig;
import net.satisfy.vinery.forge.core.registry.VineryForgeVillagers;
import net.minecraftforge.registries.ForgeRegistries;
import net.satisfy.vinery.core.util.VillagerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Vinery.MOD_ID)
public class VineryForgeEventhandler {

    public VineryForgeEventhandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType().equals(VineryForgeVillagers.WINEMAKER.get())) {
            Map<Integer, List<VillagerTrades.ItemListing>> trades = new HashMap<>();

            loadTradesFromConfig(trades, VineryForgeConfig.LEVEL1_TRADES.get(), 1);
            loadTradesFromConfig(trades, VineryForgeConfig.LEVEL2_TRADES.get(), 2);
            loadTradesFromConfig(trades, VineryForgeConfig.LEVEL3_TRADES.get(), 3);
            loadTradesFromConfig(trades, VineryForgeConfig.LEVEL4_TRADES.get(), 4);
            loadTradesFromConfig(trades, VineryForgeConfig.LEVEL5_TRADES.get(), 5);

            event.getTrades().clear();
            event.getTrades().putAll(trades);
        }
    }

    private static void loadTradesFromConfig(Map<Integer, List<VillagerTrades.ItemListing>> trades, List<? extends String> configList, int level) {
        List<VillagerTrades.ItemListing> tradeList = new ArrayList<>();
        for (String entry : configList) {
            String[] parts = entry.split("\\|");
            if (parts.length != 5) continue;

            String itemName = parts[0];
            int price = Integer.parseInt(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int maxUses = Integer.parseInt(parts[3]);
            boolean isSelling = Boolean.parseBoolean(parts[4]);

            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
            if (item != null) {
                VillagerTrades.ItemListing listing;
                if (isSelling) {
                    listing = new VillagerUtil.SellItemFactory(item, price, quantity, maxUses);
                } else {
                    listing = new VillagerUtil.BuyForOneEmeraldFactory(item, price, quantity, maxUses);
                }
                tradeList.add(listing);
            }
        }
        trades.put(level, tradeList);
    }
}
