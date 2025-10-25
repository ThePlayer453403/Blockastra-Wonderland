package com.github.blockastra;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.IntValue RESIN_STORAGE_LIMIT = BUILDER
            .defineInRange("resinStorageLimit", 2000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue RESIN_REGENERATES_LIMIT = BUILDER
            .defineInRange("resinRegeneratesLimit", 200, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue RESIN_REGENERATES_SPEED = BUILDER
            .defineInRange("resinRegeneratesSpeed", 8, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue FRAGILE_RESIN_RESTORE_AMOUNT = BUILDER
            .defineInRange("fragileResinRestoreAmount", 60, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec SPEC = BUILDER.build();
}
