/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.event.events.misc;

import net.minecraft.entity.player.EntityPlayer;
import xyz.Dot.event.Event;

public class EventInventory
extends Event {
    private final EntityPlayer player;

    public EventInventory(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }
}

