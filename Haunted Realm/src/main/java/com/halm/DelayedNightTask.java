package com.halm;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DelayedNightTask {

    private static final List<Task> tasks = new LinkedList<>();
    private static boolean hasNightBeenSet = false; // ← добавляем флаг

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            Iterator<Task> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                task.ticksLeft--;
                if (task.ticksLeft <= 0) {
                    if (!hasNightBeenSet) {
                        task.world.setTimeOfDay(18000); // 🌑 Полночь
                        hasNightBeenSet = true; // 🔒 Больше не позволим
                        System.out.println("✅ Полночь наступила!");
                    } else {
                        System.out.println("⚠️ Ночь уже была вызвана, пропускаем.");
                    }
                    iterator.remove();
                }
            }
        });
    }

    public static void reset() {
        hasNightBeenSet = false;
        System.out.println("DelayedNightTask: Reset flag, night can be summoned again.");
    }

    public static void schedule(ServerWorld world, int ticks) {
        // ⚠️ Не добавляем задачу, если ночь уже наступала
        if (!hasNightBeenSet) {
            tasks.add(new Task(world, ticks));
        } else {
            System.out.println("🚫 Попытка вызвать ночь повторно — запрещено.");
        }
    }

    private static class Task {
        ServerWorld world;
        int ticksLeft;

        Task(ServerWorld world, int ticks) {
            this.world = world;
            this.ticksLeft = ticks;
        }
    }
}
