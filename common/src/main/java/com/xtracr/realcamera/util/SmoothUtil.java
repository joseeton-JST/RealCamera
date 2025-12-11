package com.xtracr.realcamera.util;

import com.xtracr.realcamera.config.ConfigFile;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Quaterniond;

public class SmoothUtil {
    private static final Quaterniond lastRotation = new Quaterniond();
    private static Vec3 lastPosition = Vec3.ZERO;
    private static long lastPositionUpdateMs = 0L;
    private static long lastRotationUpdateMs = 0L;

    public static Vec3 smoothPosition(Vec3 position) {
        long now = System.currentTimeMillis();
        if (lastPositionUpdateMs == 0L) lastPositionUpdateMs = now;
        double delayMs = ConfigFile.config().getReturnDelayMs();
        double elapsed = now - lastPositionUpdateMs;
        double timeWeight = delayMs <= 0 ? 1.0 : Math.min(1.0, elapsed / delayMs);
        double smoothing = 1 - ConfigFile.config().getDisplacementSmoothFactor();
        lastPosition = lastPosition.lerp(position, smoothing * timeWeight);
        lastPositionUpdateMs = now;
        return lastPosition;
    }

    public static Matrix3f smoothRotation(Matrix3f rotation) {
        return smoothRotation(new Quaterniond().setFromNormalized(rotation)).get(new Matrix3f());
    }

    public static Quaterniond smoothRotation(Quaterniond rotation) {
        long now = System.currentTimeMillis();
        if (lastRotationUpdateMs == 0L) lastRotationUpdateMs = now;
        double delayMs = ConfigFile.config().getReturnDelayMs();
        double elapsed = now - lastRotationUpdateMs;
        double timeWeight = delayMs <= 0 ? 1.0 : Math.min(1.0, elapsed / delayMs);
        double smoothing = 1 - ConfigFile.config().getRotationSmoothFactor();
        lastRotation.slerp(rotation, smoothing * timeWeight);
        lastRotationUpdateMs = now;
        return lastRotation;
    }

    public static void reset() {
        lastPosition = Vec3.ZERO;
        lastPositionUpdateMs = 0L;
        lastRotation.identity();
        lastRotationUpdateMs = 0L;
    }
}
