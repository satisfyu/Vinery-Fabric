package net.satisfy.vinery.forge.core.packs;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.PackSource;
import org.jetbrains.annotations.NotNull;

public class BuiltinResourcePackSource implements PackSource {
    private final String modId;

    public BuiltinResourcePackSource(String modId) {
        this.modId = modId;
    }

    public boolean shouldAddAutomatically() {
        return true;
    }

    public @NotNull Component decorate(@NotNull Component packName) {
        return Component.translatable("pack.nameAndSource", new Object[]{packName, Component.translatable("pack.source.builtinMod", new Object[]{this.modId})}).withStyle(ChatFormatting.GRAY);
    }
}
