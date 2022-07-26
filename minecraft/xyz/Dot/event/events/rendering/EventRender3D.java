/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.event.events.rendering;


import net.optifine.shaders.Shaders;
import xyz.Dot.event.Event;

public class EventRender3D
extends Event {
    private float ticks;
    private boolean isUsingShaders;

    public EventRender3D() {
        this.isUsingShaders = Shaders.getShaderPackName() != null;
    }

    public EventRender3D(float ticks) {
        this.ticks = ticks;
        this.isUsingShaders = Shaders.getShaderPackName() != null;
    }

    public float getPartialTicks() {
        return this.ticks;
    }

    public boolean isUsingShaders() {
        return this.isUsingShaders;
    }
}

