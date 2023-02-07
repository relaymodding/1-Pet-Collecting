package org.relaymodding.petcollecting.util;

public class TickFunctions {

    /**
     * Checks if the wait amount of ticks has passed.
     */
    public static boolean isPast(int currentTime, int previousTime, int waitTicks) {
        return previousTime + waitTicks <= currentTime;
    }

    /**
     * Checks if the wait amount of ticks has passed.
     */
    public static boolean isPast(long currentTime, long previousTime, long waitTicks) {
        return previousTime + waitTicks <= currentTime;
    }

    public static int ticksPast(int currentTime, int previousTime) {
        return currentTime - previousTime;
    }
}
