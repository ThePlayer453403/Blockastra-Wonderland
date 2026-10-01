package com.blockgi.blockastra.client.render;

import com.blockgi.blockastra.BlockastraWonderland;
import com.blockgi.blockastra.data.ModDataAttachment;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HealthBarRender {
    private static final float HEALTH_BAR_Y = -12;
    private static final float HEALTH_BAR_HEIGHT = 1.8f;
    public static final Identifier ELEMENT_FONT = BlockastraWonderland.of("element_icon");


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
                instance.healthRed = livingEntity.getHealth();
                instance.healthMax = livingEntity.getMaxHealth();
                if (System.currentTimeMillis() - lastProcessTime >= 16) {
                    if (instance.healthRed < instance.healthYellow) {
                        instance.healthYellow -= instance.healthMax * 0.01f;
                    }
                    if (instance.healthRed > instance.healthYellow) {
                        instance.healthYellow = instance.healthRed;
                    }
                }
                int elementAura = 0b0000000;
                elementAura = elementAura | (livingEntity.getAttachedOrElse(ModDataAttachment.HAS_PYRO_AURA, false) ? 1 : 0);
                elementAura = elementAura | (livingEntity.getAttachedOrElse(ModDataAttachment.HAS_HYDRO_AURA, false) ? 1 << 1 : 0);
                elementAura = elementAura | (livingEntity.getAttachedOrElse(ModDataAttachment.HAS_DENDRO_AURA, false) ? 1 << 2 : 0);
                elementAura = elementAura | (livingEntity.getAttachedOrElse(ModDataAttachment.HAS_ELECTRO_AURA, false) ? 1 << 3 : 0);
                elementAura = elementAura | (livingEntity.getAttachedOrElse(ModDataAttachment.HAS_ANEMO_AURA, false) ? 1 << 4 : 0);
                elementAura = elementAura | (livingEntity.getAttachedOrElse(ModDataAttachment.HAS_CRYO_AURA, false) ? 1 << 5 : 0);
                elementAura = elementAura | (livingEntity.getAttachedOrElse(ModDataAttachment.HAS_GEO_AURA, false) ? 1 << 6 : 0);
                instance.elementAura = elementAura;
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

        FormattedCharSequence elementComponent = getElementComponent(instance);
        poseStack.pushPose();
        poseStack.scale(0.1f, 0.1f, 0.1f);
        collector.submitText(poseStack, Minecraft.getInstance().font.width(elementComponent) / -2f, HEALTH_BAR_Y * 10 - 60, elementComponent,false, Font.DisplayMode.SEE_THROUGH, 0xf000f0, 0xffffffff,0,0);
        poseStack.popPose();

        float x = instance.width / -2f;
        // 被玄学玩意吓哭了
        collector.submitTextBackground(poseStack, x - 0.1f, HEALTH_BAR_Y - 0.1f, 0.1f - x, HEALTH_BAR_Y + HEALTH_BAR_HEIGHT + 0.1f, 0xff3a222d, Font.DisplayMode.NORMAL, 0xffffff);
        poseStack.translate(0, 0, 0.001f);
        collector.submitTextBackground(poseStack, x, HEALTH_BAR_Y, instance.width * (instance.healthYellow / instance.healthMax) + x, HEALTH_BAR_Y + HEALTH_BAR_HEIGHT, 0xffffd17e, Font.DisplayMode.NORMAL, 0xf000f0);
        poseStack.translate(0, 0, 0.001f);
        collector.submitTextBackground(poseStack, x, HEALTH_BAR_Y, instance.width * (instance.healthRed / instance.healthMax) + x, HEALTH_BAR_Y + HEALTH_BAR_HEIGHT, 0xffff5a5a, Font.DisplayMode.NORMAL, 0xf000f0);
        poseStack.popPose();
    }

    private static @NonNull FormattedCharSequence getElementComponent(LivingEntityHealthBar instance) {
        StringBuilder element = new StringBuilder();
        if ((instance.elementAura & 1) == 1) {
            element.append("\u0001");
        }
        if ((instance.elementAura >> 1 & 1) == 1) {
            element.append("\u0002");
        }
        if ((instance.elementAura >> 2 & 1) == 1) {
            element.append("\u0003");
        }
        if ((instance.elementAura >> 3 & 1) == 1) {
            element.append("\u0004");
        }
        if ((instance.elementAura >> 4 & 1) == 1) {
            element.append("\u0005");
        }
        if ((instance.elementAura >> 5 & 1) == 1) {
            element.append("\u0006");
        }
        if ((instance.elementAura >> 6 & 1) == 1) {
            element.append("\u0007");
        }

        return Component.literal(element.toString()).setStyle(Style.EMPTY.withFont(new FontDescription.Resource(ELEMENT_FONT))).getVisualOrderText();
    }

    public static class LivingEntityHealthBar {
        public float healthMax = 1;
        public float healthRed = 1;
        public float healthYellow = 1;
        public double x = 0;
        public double y = 0;
        public double z = 0;
        public float width;
        public UUID uuid;
        public int elementAura = 0b0000000;

        public LivingEntityHealthBar(UUID uuid, float width) {
            this.uuid = uuid;
            this.width = width;
        }
    }
}
