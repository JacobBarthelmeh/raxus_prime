/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raxus_prime;

/**
 * A simple bloomfilter. Uses murmur and FNV to create n (hopefully unique)
 * hashes.
 *
 * @author Alex
 */
public class CountingFilter {

    private final byte[] filter;
    private final int numHashes;
    private final int added;
    private final int length;
    private final int expected;

    /**
     *
     * @param length The number of bits to allocate for the filter.
     * @param expectedInsertions The number of elements that are expected to be
     * added.
     */
    public CountingFilter(int length, int expectedInsertions) {
        this.length = length;
        this.expected = expectedInsertions;
        filter = new byte[length];
        numHashes = (int) (((double) length) / expectedInsertions * 0.693147181);
        added = 0;
    }

    /**
     * Adds an element to the bloom filter.
     *
     * @param data The object to be added, represented by a byte array
     * @return The number of times a bit was set, that was previously set.
     */
    public int add(byte[] data) {
        return adddel(data,1);
    }

    private int adddel(byte[] data, int val) {
        int collisions = 0;
        int oldHash = 31; //uses 31 as an initial seed because it seems like a good number.
        for (int i = 0; i < numHashes; i++) {
            int hash = Math.abs((int) Hashing.getHash(data, numHashes, oldHash)) % length;
            oldHash = hash;
            if (filter[hash] > 0) {
                collisions++;
            }
            filter[hash]+=val;
        }

        return collisions;
    }

    public void remove(byte[] data) {
        adddel(data,-1);
    }

    /**
     *
     * @param data The object to be queried against.
     * @return True if this object has probably been added before. False if it
     * has definitely never been added.
     */
    public boolean isIn(byte[] data) {
        int oldHash = 31; //31 again. Good number that 31.
        for (int i = 0; i < numHashes; i++) {
            int hash = Math.abs((int) Hashing.getHash(data, numHashes, oldHash)) % length;
            if (filter[hash] == 0) {
                return false;
            }
            oldHash = hash;
        }
        return true;
    }

    /**
     *
     * @return The number of elements that have been added.
     */
    public int numAdded() {
        return added;
    }

    /**
     *
     * @return the number that this bloomfilter anticipated being added.
     */
    public int expected() {
        return expected;
    }

    /**
     *
     * @return True if you lied and added too many things. False if you are a
     * man of your word.
     */
    public boolean didYouLieToMe() {
        return (added > expected);
    }

}
