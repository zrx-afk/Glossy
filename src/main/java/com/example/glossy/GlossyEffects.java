package com.example.glossy;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

/** Blue and cyan particle effects. All of them can be switched off in glossy.json. */
public class GlossyEffects {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(GlossyEffects::tick);
        AttackEntityCallback.EVENT.register(GlossyEffects::onAttack);
    }

    private static void tick(Minecraft mc) {
        Player player = mc.player;
        Level level = mc.level;
        if (player == null || level == null || mc.isPaused()) {
            return;
        }
        GlossyConfig cfg = GlossyClient.config;
        int amount = cfg.amount();
        if (amount == 0) {
            return;
        }
        RandomSource r = player.getRandom();

        // Cyan flame trail while sprinting
        if (cfg.sprintTrail && player.isSprinting()) {
            for (int i = 0; i < amount; i++) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                        player.getX() + (r.nextDouble() - 0.5) * 0.4,
                        player.getY() + 0.1,
                        player.getZ() + (r.nextDouble() - 0.5) * 0.4,
                        0.0, 0.02, 0.0);
            }
        }

        // Sparkles around you while holding an enchanted item
        if (cfg.enchantSparkles && player.tickCount % 4 == 0) {
            ItemStack held = player.getMainHandItem();
            if (held.isEnchanted()) {
                for (int i = 0; i < amount; i++) {
                    level.addParticle(ParticleTypes.ENCHANT,
                            player.getX() + (r.nextDouble() - 0.5),
                            player.getY() + 1.0 + r.nextDouble() * 0.6,
                            player.getZ() + (r.nextDouble() - 0.5),
                            0.0, 0.1, 0.0);
                }
            }
        }
    }

    private static InteractionResult onAttack(Player player, Level level, InteractionHand hand,
                                              Entity target, EntityHitResult hit) {
        GlossyConfig cfg = GlossyClient.config;
        int amount = cfg.amount();
        if (cfg.hitEffects && amount > 0 && level.isClientSide()) {
            RandomSource r = player.getRandom();
            double x = target.getX();
            double y = target.getY() + target.getBbHeight() * 0.5;
            double z = target.getZ();
            for (int i = 0; i < amount * 8; i++) {
                level.addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z,
                        (r.nextDouble() - 0.5) * 0.6, (r.nextDouble() - 0.3) * 0.5, (r.nextDouble() - 0.5) * 0.6);
            }
            for (int i = 0; i < amount * 3; i++) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z,
                        (r.nextDouble() - 0.5) * 0.3, r.nextDouble() * 0.2, (r.nextDouble() - 0.5) * 0.3);
            }
        }
        return InteractionResult.PASS;
    }
}
