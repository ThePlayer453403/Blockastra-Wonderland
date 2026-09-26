package com.blockgi.blockastra.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HealthBarRender {
    private static final List<LivingEntityHealthBar> livingEntityHealthBarItem = new ArrayList<>();
    private static long lastProcessTime = 0;

    public static void register() {
        LevelExtractionEvents.END_EXTRACTION.register(HealthBarRender::extractHealthBar);
        LevelRenderEvents.COLLECT_SUBMITS.register(HealthBarRender::renderHealthBar);
    }

    public static void extractHealthBar(LevelExtractionContext context) {
        if (Minecraft.getInstance().level != null) {
            Map<UUID, LivingEntity> entityMap = new HashMap<>();
            Minecraft.getInstance().level.entitiesForRendering().forEach(entity -> {
                if (entity instanceof LivingEntity livingEntity) {
                    if (Minecraft.getInstance().player != null && livingEntity instanceof Player player && player.getPlainTextName().equals(Minecraft.getInstance().player.getPlainTextName())){
                        return;
                    }
                    entityMap.put(entity.getUUID(), livingEntity);
                    AtomicBoolean contains = new AtomicBoolean(false);
                    livingEntityHealthBarItem.forEach(healthBar -> contains.set(contains.get() || healthBar.uuid == livingEntity.getUUID()));
                    if (!contains.get()) {
                        livingEntityHealthBarItem.add(new LivingEntityHealthBar(livingEntity.getUUID(), 30));
                    }
                }
            });
            livingEntityHealthBarItem.removeIf(entity -> !entityMap.containsKey(entity.uuid));
            livingEntityHealthBarItem.forEach(instance -> {
                LivingEntity livingEntity = entityMap.get(instance.uuid);
                instance.x = livingEntity.position().x;
                instance.y = livingEntity.position().y + livingEntity.getBbHeight();
                instance.z = livingEntity.position().z;
                instance.health_red = livingEntity.getHealth();
                instance.health_max = livingEntity.getMaxHealth();
                if (System.currentTimeMillis() - lastProcessTime >= 16) {
                    if (instance.health_red < instance.health_yellow) {
                        instance.health_yellow -= instance.health_max * 0.01f;
                    }
                    if (instance.health_red > instance.health_yellow) {
                        instance.health_yellow = instance.health_red;
                    }
                }
            });
            if (System.currentTimeMillis() - lastProcessTime >= 16) {
                lastProcessTime = System.currentTimeMillis();
            }
        }
    }

    public static void renderHealthBar(LevelRenderContext context) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) return;

        PoseStack poseStack = context.poseStack();
        Vec3 cameraPos = context.levelState().cameraRenderState.pos;
        SubmitNodeCollector collector = context.submitNodeCollector();

        livingEntityHealthBarItem.forEach(instance -> renderHealthBar(client, poseStack, cameraPos, collector, instance));
    }

    private static void renderHealthBar(Minecraft client, PoseStack poseStack, Vec3 cameraPos, SubmitNodeCollector collector, LivingEntityHealthBar instance) {
        poseStack.pushPose();
        poseStack.translate(instance.x - cameraPos.x, instance.y - cameraPos.y, instance.z - cameraPos.z);

        if (client.player != null) {
            poseStack.rotateDegrees(Axis.YN, client.player.getYRot());
            poseStack.rotateDegrees(Axis.XP, client.player.getXRot());
        }

        poseStack.scale(-0.05f, -0.05f, -0.05f);
        float x = instance.width / -2f;
        // 被玄学玩意吓哭了
        collector.submitTextBackground(poseStack, x, -8, -x, -6.2f, 0xff000000, Font.DisplayMode.NORMAL, 0xff3a222d);
        poseStack.translate(0, 0, 0.001f);
        collector.submitTextBackground(poseStack, x, -8, instance.width * (instance.health_yellow / instance.health_max) + x, -6.2f, 0xffffd17e, Font.DisplayMode.NORMAL, 0xf000f0);
        poseStack.translate(0, 0, 0.001f);
        collector.submitTextBackground(poseStack, x, -8, instance.width * (instance.health_red / instance.health_max) + x, -6.2f, 0xffff5a5a, Font.DisplayMode.NORMAL, 0xf000f0);
        poseStack.popPose();
    }

    public static class LivingEntityHealthBar {
        public float health_max = 1;
        public float health_red = 1;
        public float health_yellow = 1;
        public double x = 0;
        public double y = 0;
        public double z = 0;
        public float width;
        public UUID uuid;

        public LivingEntityHealthBar(UUID uuid, float width) {
            this.uuid = uuid;
            this.width = width;
        }
    }
}
