package com.halm.item;

import com.halm.DelayedNightTask;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class KokashkItem extends Item {

    public KokashkItem(Settings settings) {
        super(settings.food(new FoodComponent.Builder()
                .hunger(0)
                .saturationModifier(0f)
                .alwaysEdible()
                .build()));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // 🌙 Добавим отложенное наступление ночи через 3 секунды
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            DelayedNightTask.schedule(serverWorld, 60); // 60 тиков = 3 секунды
        }

        // 🔄 Здесь можно оставить твою собственную логику
        // Например, отправка сообщения, эффект, звук, удаление предмета и т.д.
        // Пример:
        /*
        if (!world.isClient) {
            user.sendMessage(Text.literal("Ты съел кокашк..."), false);
        }
        */

        return super.finishUsing(stack, world, user); // обязательно вызываем родительскую реализацию
    }
}
