package couk.adamki11s.npcs.tasks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.entity.EntityType;

public class EntityKillTracker {

	HashSet<EntityType> types = new HashSet<EntityType>();
	HashMap<EntityType, Integer> required = new HashMap<EntityType, Integer>();
	HashMap<EntityType, Integer> current = new HashMap<EntityType, Integer>();

	public EntityKillTracker(String in) {
		this.parseInput(in);
	}

	void parseInput(String in) {
		String[] ents = in.split(",");
		for (String parse : ents) {
			String[] components = parse.split(":");
			EntityType e = EntityType.valueOf(components[0]);
			types.add(e);
			int k = Integer.parseInt(components[1]);
			this.required.put(e, k);
		}
	}

	public void trackKill(EntityType e) {
		if (this.types.contains(e)) {
			int cur = this.current.get(e) + 1;
			this.current.put(e, cur);
		}
	}

	public boolean areRequiredEntitiesKilled() {
		for (Map.Entry<EntityType, Integer> entry : this.required.entrySet()) {
			EntityType e = entry.getKey();
			int reqKills = entry.getValue();
			if (this.current.get(e) < reqKills) {
				return false;
			}
		}
		return true;
	}

	public boolean areKillsCompleted() {
		// TODO
		return false;
	}

}
