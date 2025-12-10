package com.xtracr.realcamera.util;

import net.minecraft.client.Minecraft;

public final class DevSupport {
    private static final boolean AUTO_EXIT = Boolean.getBoolean("realcamera.autoExitAfterLaunch");
    private static boolean stopQueued = false;

    public static boolean autoExitAfterLaunch() {
        return AUTO_EXIT;
    }

    public static void requestStop(Minecraft client) {
        if (AUTO_EXIT && !stopQueued) {
            stopQueued = true;
            client.execute(client::stop);
        }
    }

    private DevSupport() {}
}
