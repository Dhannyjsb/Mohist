package org.bukkit.material;

import org.bukkit.Material;

public class Cake extends MaterialData {
    public Cake() {
        super(Material.CAKE_BLOCK);
    }

    /**
     * @param type the raw type id
     * @deprecated Magic value
     */
    
    public Cake(int type) {
        super(type);
    }

    public Cake(Material type) {
        super(type);
    }

    /**
     * @param type the raw type id
     * @param data the raw data value
     * @deprecated Magic value
     */
    
    public Cake(int type, byte data) {
        super(type, data);
    }

    /**
     * @param type the type
     * @param data the raw data value
     * @deprecated Magic value
     */
    
    public Cake(Material type, byte data) {
        super(type, data);
    }

    /**
     * Gets the number of slices eaten from this cake
     *
     * @return The number of slices eaten
     */
    public int getSlicesEaten() {
        return getData();
    }

    /**
     * Gets the number of slices remaining on this cake
     *
     * @return The number of slices remaining
     */
    public int getSlicesRemaining() {
        return 6 - getData();
    }

    /**
     * Sets the number of slices eaten from this cake
     *
     * @param n The number of slices eaten
     */
    public void setSlicesEaten(int n) {
        if (n < 6 && n >=0) {
            setData((byte) n);
        } // TODO: else destroy the block? Probably not possible though
    }

    /**
     * Sets the number of slices remaining on this cake
     *
     * @param n The number of slices remaining
     */
    public void setSlicesRemaining(int n) {
        if (n > 6) {
            n = 6;
        }
        setData((byte) (6 - n));
    }

    @Override
    public String toString() {
        return super.toString() + " " + getSlicesEaten() + "/" + getSlicesRemaining() + " slices eaten/remaining";
    }

    @Override
    public Cake clone() {
        return (Cake) super.clone();
    }
}
