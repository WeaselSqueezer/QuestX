package com.adamki11s.events;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.adamki11s.dialogue.Conversation;
import com.adamki11s.npcs.NPCHandler;

public class ConversationRegister implements Listener {

	final NPCHandler handle;

	public static HashSet<Conversation> playersConversing = new HashSet<Conversation>();

	public ConversationRegister(Plugin p, NPCHandler handle) {
		Bukkit.getServer().getPluginManager().registerEvents(this, p);
		this.handle = handle;
	}

	public static boolean isPlayerConversing(String pName) {
		ArrayList<Conversation> rem = new ArrayList<Conversation>();
		for (Conversation c : playersConversing) {
			if (c != null) {
				Player convoPlayer = c.getConvoData().getPlayer();
				if (convoPlayer != null) {
					if (convoPlayer.getName().equalsIgnoreCase(pName)) {
						return c.isConversing();
					}
				} else {
					rem.add(c);
				}
			}
		}
		
		for(Conversation c : rem){
			playersConversing.remove(c);
		}
		
		return false;
	}

	public static boolean isPlayerWithinTalkingDistance(Player p) {
		ArrayList<Conversation> rem = new ArrayList<Conversation>();
		for (Conversation c : playersConversing) {
			if (c != null) {
				Player convoPlayer = c.getConvoData().getPlayer();
				if (convoPlayer != null) {
					if (convoPlayer.getName().equalsIgnoreCase(p.getName())) {
						Location npcLoc = c.getConvoData().getSimpleNpc().getHumanNPC().getBukkitEntity().getLocation();
						Location pl = p.getLocation();
						c.getConvoData().getSimpleNpc().getHumanNPC().lookAtPoint(new Location(pl.getWorld(), pl.getX(), pl.getY() + 1, pl.getZ()));
						return (Math.abs(npcLoc.distance(pl)) < 5);
					}
				} else {
					rem.add(c);
				}

			}
		}
		
		for(Conversation c : rem){
			playersConversing.remove(c);
		}
		
		return false;
	}

	public static void endPlayerNPCConversation(Player p) {
		for (Conversation c : playersConversing) {
			if (c != null) {
				if (c.getConvoData().getPlayer().getName().equalsIgnoreCase(p.getName())) {
					c.endConversation();
					c.getConvoData().getSimpleNpc().setMovementUnscheduled();
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(final AsyncPlayerChatEvent evt) {
		for (Conversation c : playersConversing) {
			if (c != null) {
				if (c.getConvoData().getPlayer().getName().equalsIgnoreCase(evt.getPlayer().getName())) {
					String s = evt.getMessage();
					if (s.equalsIgnoreCase("exit") || s.equalsIgnoreCase("end") || s.equalsIgnoreCase("stop") || s.equalsIgnoreCase("quit") || s.equalsIgnoreCase("close")) {
						c.endConversation();
						evt.setCancelled(true);
						return;
					}
					c.respond(evt.getMessage());
					evt.setCancelled(true);
				}
			}
		}
	}

}
