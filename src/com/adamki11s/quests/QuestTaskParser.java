package com.adamki11s.quests;

import org.bukkit.inventory.ItemStack;

import com.adamki11s.npcs.tasks.EntityKillTracker;
import com.adamki11s.npcs.tasks.ISAParser;
import com.adamki11s.npcs.tasks.NPCKillTracker;
import com.adamki11s.npcs.tasks.NPCTalkTracker;

public class QuestTaskParser {

	public static QuestTask getTaskObject(String input, QType type) {
		Object o = null;
		switch (type) {
		case FETCH_ITEMS:
			o = getInventoryObject(input);
			break;
		case KILL_ENTITIES:
			o = getEntityKillObject(input);
			break;
		case KILL_NPC:
			o = getNPCKillObject(input);
			break;
		case TALK_NPC:
			o = new NPCTalkTracker(input);
			break;
		}
		String npcName = input.substring(input.lastIndexOf(":"));
		return new QuestTask(type, o, npcName);
	}

	static ItemStack[] getInventoryObject(String input) {
		return ISAParser.parseISA(input);
	}

	static EntityKillTracker getEntityKillObject(String input) {
		return new EntityKillTracker(input);
	}

	static NPCKillTracker getNPCKillObject(String input) {
		return new NPCKillTracker(input);
	}

}