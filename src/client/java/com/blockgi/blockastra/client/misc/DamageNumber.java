package com.blockgi.blockastra.client.misc;

import com.blockgi.blockastra.network.DamageNumberPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class DamageNumber {
    public static List<DamageNumberInstance> damageNumberInstances = new ArrayList<>();

    public static class DamageNumberInstance {
        public float x;
        public float y;
        public float z;
        public int damage;
        public int lifetime;
        public int color;
        public float width;
        public String text;

        public DamageNumberInstance(Vec3 position, int damage, int color) {
            this.x = (float) position.x;
            this.y = (float) position.y;
            this.z = (float) position.z;
            this.damage = damage;
            this.lifetime = 200;
            this.color = color;
            this.text = String.valueOf(this.damage);
            this.width = Minecraft.getInstance().font.width(this.text);
        }
    }

    public static void addDamageNumberInstance(DamageNumberPayload payload) {
        damageNumberInstances.add(new DamageNumberInstance(payload.position(), payload.damage(), payload.color()));
    }
}
