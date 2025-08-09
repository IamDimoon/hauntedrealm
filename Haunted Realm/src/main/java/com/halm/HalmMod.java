package com.halm;

import com.halm.item.KokashkItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class HalmMod implements ModInitializer {
    public static final String MOD_ID = "halm";

    public static final Item KOKASHK = new KokashkItem(new Item.Settings());

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "kokashk"), KOKASHK);
        DelayedNightTask.init();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("tihalm")
                    .then(CommandManager.literal("reset")
                            .executes(context -> {
                                DelayedNightTask.reset();
                                context.getSource().sendFeedback(() -> Text.of("Halm night reset! You can call night again."), false);
                                return 1;
                            })
                    )
            );
        });
    }

}

