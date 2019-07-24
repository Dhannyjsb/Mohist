package org.bukkit.event.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Thrown whenever a LivingEntity dies
 */
public class EntityDeathEvent extends EntityEvent implements org.bukkit.event.Cancellable {  // Paper - make cancellable
    private static final HandlerList handlers = new HandlerList();
    private final List<ItemStack> drops;
    private int dropExp = 0;
    // Paper start - make cancellable
    private boolean cancelled;
    private double reviveHealth = 0;
    private boolean shouldPlayDeathSound;
    private org.bukkit.Sound deathSound;
    private org.bukkit.SoundCategory deathSoundCategory;
    private float deathSoundVolume;
    private float deathSoundPitch;
    // Paper end

    public EntityDeathEvent(final LivingEntity entity, final List<ItemStack> drops) {
        this(entity, drops, 0);
    }

    public EntityDeathEvent(final LivingEntity what, final List<ItemStack> drops, final int droppedExp) {
        super(what);
        this.drops = drops;
        this.dropExp = droppedExp;
    }

    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) entity;
    }

    /**
     * Gets how much EXP should be dropped from this death.
     * <p>
     * This does not indicate how much EXP should be taken from the entity in
     * question, merely how much should be created after its death.
     *
     * @return Amount of EXP to drop.
     */
    public int getDroppedExp() {
        return dropExp;
    }

    /**
     * Sets how much EXP should be dropped from this death.
     * <p>
     * This does not indicate how much EXP should be taken from the entity in
     * question, merely how much should be created after its death.
     *
     * @param exp Amount of EXP to drop.
     */
    public void setDroppedExp(int exp) {
        this.dropExp = exp;
    }

    /**
     * Gets all the items which will drop when the entity dies
     *
     * @return Items to drop when the entity dies
     */
    public List<ItemStack> getDrops() {
        return drops;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    // Paper start - make cancellable
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    /**
     * Get the amount of health that the entity should revive with after cancelling the event.
     * Set to the entity's max health by default.
     *
     * @return The amount of health
     */
    public double getReviveHealth() {
        return reviveHealth;
    }

    /**
     * Set the amount of health that the entity should revive with after cancelling the event.
     * Revive health value must be between 0 (exclusive) and the entity's max health (inclusive).
     *
     * @param reviveHealth The amount of health
     * @throws IllegalArgumentException Thrown if the health is {@literal <= 0 or >} max health
     */
    public void setReviveHealth(double reviveHealth) throws IllegalArgumentException {
        double maxHealth = ((LivingEntity) entity).getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();
        if ((maxHealth != 0 && reviveHealth <= 0) || (reviveHealth > maxHealth)) {
            throw new IllegalArgumentException("Health must be between 0 (exclusive) and " + maxHealth + " (inclusive), but was " + reviveHealth);
        }
        this.reviveHealth = reviveHealth;
    }


    /**
     * Whether or not the death sound should play when the entity dies. If the event is cancelled it does not play!
     *
     * @return Whether or not the death sound should play. Event is called with this set to false if the entity is silent.
     */
    public boolean shouldPlayDeathSound() {
        return shouldPlayDeathSound;
    }

    /**
     * Set whether or not the death sound should play when the entity dies. If the event is cancelled it does not play!
     *
     * @param playDeathSound Enable or disable the death sound
     */
    public void setShouldPlayDeathSound(boolean playDeathSound) {
        this.shouldPlayDeathSound = playDeathSound;
    }

    /**
     * Get the sound that the entity makes when dying
     *
     * @return The sound that the entity makes
     */
    public org.bukkit.Sound getDeathSound() {
        return deathSound;
    }

    /**
     * Set the sound that the entity makes when dying
     *
     * @param sound The sound that the entity should make when dying
     */
    public void setDeathSound(org.bukkit.Sound sound) {
        deathSound = sound;
    }

    /**
     * Get the sound category that the death sound should play in
     *
     * @return The sound category
     */
    public org.bukkit.SoundCategory getDeathSoundCategory() {
        return deathSoundCategory;
    }

    /**
     * Set the sound category that the death sound should play in.
     *
     * @param soundCategory The sound category
     */
    public void setDeathSoundCategory(org.bukkit.SoundCategory soundCategory) {
        this.deathSoundCategory = soundCategory;
    }

    /**
     * Get the volume that the death sound will play at.
     *
     * @return The volume the death sound will play at
     */
    public float getDeathSoundVolume() {
        return deathSoundVolume;
    }

    /**
     * Set the volume the death sound should play at. If the event is cancelled this will not play the sound!
     *
     * @param volume The volume the death sound should play at
     */
    public void setDeathSoundVolume(float volume) {
        this.deathSoundVolume = volume;
    }

    /**
     * Get the pitch that the death sound will play with.
     *
     * @return The pitch the death sound will play with
     */
    public float getDeathSoundPitch() {
        return deathSoundPitch;
    }

    /**
     * GSetet the pitch that the death sound should play with.
     *
     * @param pitch The pitch the death sound should play with
     */
    public void setDeathSoundPitch(float pitch) {
        this.deathSoundPitch = pitch;
    }
    // Paper end
}