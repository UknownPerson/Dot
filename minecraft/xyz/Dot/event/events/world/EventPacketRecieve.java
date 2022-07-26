/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.event.events.world;

import net.minecraft.network.Packet;
import xyz.Dot.event.Event;

public class EventPacketRecieve
extends Event {
    private Packet packet;

    public EventPacketRecieve(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

