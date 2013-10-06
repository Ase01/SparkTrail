package com.github.dsh105.sparktrail.util;

import com.github.dsh105.sparktrail.particle.EffectHolder;
import com.github.dsh105.sparktrail.particle.ParticleType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Project by DSH105
 */

public enum Permission {

	UPDATE("sparktrail.update", ""),
	TRAIL("sparktrail.trail", "", "sparktrail.trail.*", "sparktrail.*"),
	EFFECT("sparktrail.trail.%effect%", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
	DEMO("sparktrail.trail.demo", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
	INFO("sparktrail.trail.info", "sparktrail.trail", "sparktrail.trail.*", "sparktrail.*"),
	;

	String perm;
	String requiredPerm;
	ArrayList<String> hierarchy = new ArrayList<String>();

	Permission(String perm, String requiredPerm, String... hierarchy) {
		this.perm = perm;
		this.requiredPerm = requiredPerm;
		for (String s : hierarchy) {
			this.hierarchy.add(s);
		}
	}

	public boolean hasPerm(CommandSender sender, boolean sendMessage, boolean allowConsole) {
		if (sender instanceof Player) {
			return hasPerm(((Player) sender), sendMessage);
		}
		else {
			if (!allowConsole && sendMessage) {
				Lang.sendTo(sender, Lang.IN_GAME_ONLY.toString());
			}
			return allowConsole;
		}
	}

	public boolean hasPerm(Player player, boolean sendMessage) {
		boolean hasRequiredPerm = this.requiredPerm.equalsIgnoreCase("") ? true : player.hasPermission(this.requiredPerm);
		if (!(player.hasPermission(this.perm) && hasRequiredPerm)) {
			for (String s : this.hierarchy) {
				if (player.hasPermission(s)) {
					return true;
				}
			}
			if (sendMessage) {
				Lang.sendTo(player, Lang.NO_PERMISSION.toString().replace("%perm%", this.perm));
			}
			return false;
		}
		else {
			return true;
		}
	}

	public static boolean hasEffectPerm(Player player, boolean sendMessage, String perm, EffectHolder.EffectType effectType) {
		if (!(player.hasPermission(perm) && player.hasPermission("sparktrail.trail"))) {
			for (String s : EFFECT.hierarchy) {
				if (player.hasPermission(s)) {
					return true;
				}
			}
			if (player.hasPermission("sparktrail.trail." + effectType.toString().toLowerCase() + ".type.*") || player.hasPermission("sparktrail.trail.type.*")) {
				return true;
			}
			if (sendMessage) {
				Lang.sendTo(player, Lang.NO_PERMISSION.toString().replace("%perm%", perm));
			}
			return false;
		}
		else {
			return true;
		}
	}

	public static boolean hasEffectPerm(Player player, boolean sendMessage, ParticleType particleType, EffectHolder.EffectType effectType) {
		String perm = "sparktrail.trail." + effectType.toString().toLowerCase() + ".type." + particleType.toString().toLowerCase();
		return hasEffectPerm(player, sendMessage, perm, effectType) || hasEffectPerm(player, sendMessage, perm + ".*", effectType);
	}

	public static boolean hasEffectPerm(Player player, boolean sendMessage, ParticleType particleType, String data, EffectHolder.EffectType effectType) {
		String perm = "sparktrail.trail." + effectType.toString().toLowerCase() + ".type." + particleType.toString().toLowerCase();
		return hasEffectPerm(player, sendMessage, perm + "." + data, effectType) || hasEffectPerm(player, sendMessage, perm + ".*", effectType);
	}
}