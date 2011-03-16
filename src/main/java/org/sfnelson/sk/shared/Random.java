package org.sfnelson.sk.shared;


public class Random {

    /**
     * Returns a random number between {@code 1} and {@code 100} (inclusive).
     * This method will always return the same number for this user.
     *
     * This method is intended for use in seeding a user's position in ranking
     * algorithms.
     */
    public static int getRandom(long seed) {
        return getRandom(seed, 1, 100);
    }

    /**
     * Returns a random number between {@code 1} and {@code max} (inclusive).
     * This method will always return the same number for this user, given the
     * same parameter.
     *
     * This method is intended for use in seeding a user's position in ranking
     * algorithms.
     */
    public static int getRandom(long seed, int max) {
        return getRandom(seed, 1, max);
    }

    /**
     * Returns a random number between {@code min} and {@code max} (inclusive).
     * This method will always return the same number for this user, given the
     * same parameters.
     *
     * This method is intended for use in seeding a user's position in ranking
     * algorithms.
     */
    public static int getRandom(long seed, int min, int max) {
        max = max + 1;
        Random r = new Random(seed);
        return min + r.nextInt(max - min);
    }

    private long seed;

    public Random(long seed) {
        this.seed = seed;
    }

    public int nextInt(int n) {
        // Algorithm from java.util.Random
        if ((n & -n) == n) {
            return (int)((n * (long)next(31)) >> 31);
        }

        int bits, val;
        do {
            bits = next(31);
            val = bits % n;
        } while(bits - val + (n-1) < 0);
        return val;
    }

    private int next(int bits) {
        seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
        return (int)(seed >>> (48 - bits));
    }
}
