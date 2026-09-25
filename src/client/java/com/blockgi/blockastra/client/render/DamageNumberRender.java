package com.blockgi.blockastra.client.render;

import com.blockgi.blockastra.client.misc.DamageNumber;
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
import net.minecraft.world.phys.Vec3;


public class DamageNumberRender {
    public static void register() {
        LevelExtractionEvents.END_EXTRACTION.register(DamageNumberRender::extractDamageNumbers);
        LevelRenderEvents.COLLECT_SUBMITS.register(DamageNumberRender::renderDamageNumbers);
    }

    public static void extractDamageNumbers(LevelExtractionContext context) {
        DamageNumber.damageNumberInstances.removeIf(instance -> --instance.lifetime <= 0);
    }

    public static void renderDamageNumbers(LevelRenderContext context) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) return;

        PoseStack poseStack = context.poseStack();
        Vec3 cameraPos = context.levelState().cameraRenderState.pos;
        SubmitNodeCollector collector = context.submitNodeCollector();

        DamageNumber.damageNumberInstances.forEach(instance -> renderDamageNumber(client, poseStack, cameraPos, collector, instance));
    }

    private static void renderDamageNumber(Minecraft client, PoseStack poseStack, Vec3 cameraPos, SubmitNodeCollector collector, DamageNumber.DamageNumberInstance instance) {
        poseStack.pushPose();
        poseStack.translate(instance.x - cameraPos.x, instance.y - cameraPos.y, instance.z - cameraPos.z);

        if (client.player != null) {
            poseStack.rotateDegrees(Axis.YN, client.player.getYRot());
            poseStack.rotateDegrees(Axis.XP, client.player.getXRot());
        }
        float scale = -Math.max(3f / (210 - instance.lifetime), 0.05f);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0, instance.lifetime * 0.05f, 0);
        collector.submitText(poseStack, -instance.width / 2f, -client.font.lineHeight / 2f, Component.literal(instance.text).getVisualOrderText(), false, Font.DisplayMode.SEE_THROUGH, 0xF000F0, instance.color | Math.min(instance.lifetime * 3, 255) << 24,0,0);
        poseStack.popPose();
    }

}
