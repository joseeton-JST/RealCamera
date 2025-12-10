package com.xtracr.realcamera.util;

import com.xtracr.realcamera.RealCamera;
import net.minecraft.client.Minecraft;

public final class DevSupport {
    private static final boolean AUTO_EXIT = Boolean.parseBoolean(System.getProperty(
            "realcamera.autoExitAfterLaunch",
            System.getenv().getOrDefault("REALCAMERA_AUTO_EXIT", "false")));
    private static boolean stopQueued = false;

    public static boolean autoExitAfterLaunch() {
        return AUTO_EXIT;
    }

    public static void requestStop(Minecraft client) {
        if (AUTO_EXIT && !stopQueued) {
            stopQueued = true;
            RealCamera.LOGGER.info("Auto-exiting runClient after launch");
            client.execute(client::stop);
        }
    }

    private DevSupport() {}
}
