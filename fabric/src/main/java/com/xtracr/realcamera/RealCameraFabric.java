package com.xtracr.realcamera;

import com.xtracr.realcamera.util.DevSupport;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class RealCameraFabric implements ClientModInitializer, RealCamera {
    @Override
    public void onInitializeClient() {
        initialize();
        KeyMappings.register(KeyBindingHelper::registerKeyBinding);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            KeyMappings.handle(client);
            DevSupport.requestStop(client);
        });
        WorldRenderEvents.START.register(EventHandler::onWorldRenderStart);

        if (DevSupport.autoExitAfterLaunch()) {
            net.minecraft.client.Minecraft.getInstance().stop();
            return;
        }
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
