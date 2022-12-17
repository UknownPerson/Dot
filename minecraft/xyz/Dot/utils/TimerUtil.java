/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.utils;

public class TimerUtil {
    private long lastMS;

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(double milliseconds) {
        if ((double)(this.getCurrentMS() - this.lastMS) >= milliseconds) {
            return true;
        }
        return false;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(float milliSec) {
        if ((float) (this.getTime() - this.lastMS) >= milliSec) {
            return true;
        }
        return false;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public long gettime() {
        return this.getCurrentMS() - this.lastMS;
    }
}

