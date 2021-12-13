package net.waterfallflower.telemetrymod;

public class ThreadLoader {
    public static void runThread(Runnable runnable, long nanoseconds) {
        new Thread(() -> {
            try {
                Thread.sleep(nanoseconds);
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
