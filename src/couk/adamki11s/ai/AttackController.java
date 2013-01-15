package couk.adamki11s.ai;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.topcat.npclib.entity.HumanNPC;

import couk.adamki11s.ai.dataset.Reputation;
import couk.adamki11s.npcs.BanditNPC;
import couk.adamki11s.npcs.NPCHandler;
import couk.adamki11s.npcs.SimpleNPC;

public class AttackController {

	HashMap<HumanNPC, Player> target = new HashMap<HumanNPC, Player>();

	final NPCHandler handle;

	public AttackController(NPCHandler handle) {
		this.handle = handle;
	}

	public synchronized void run() {
		for (SimpleNPC npc : handle.getNPCs()) {
			if (npc.isUnderAttack() && npc.isNPCSpawned()) {
				this.retalliate(npc.getAggressor(), npc);
			}
		}
	}

	synchronized void retalliate(Player p, SimpleNPC npc) {
		// if(p is inside npc area) then attack

		Location loc = p.getLocation();
		int var = npc.getMaxVariation() + ((int)npc.getMaxVariation() / 4); //Adds 1/4th to the attack radius
		Location root = npc.getRootLocation();
		Location bl = new Location(loc.getWorld(), root.getBlockX() - var, root.getBlockY() - var, root.getBlockZ() - var), tr = new Location(loc.getWorld(), root.getBlockX()
				+ var, root.getBlockY() + var, root.getBlockZ() + var);

		if (loc.getBlockX() > bl.getBlockX() && loc.getBlockY() > bl.getBlockY() && loc.getBlockZ() > bl.getBlockZ() && loc.getBlockX() < tr.getBlockX()
				&& loc.getBlockY() < tr.getBlockY() && loc.getBlockZ() < tr.getBlockZ()) {

			p.sendMessage("In attack zone!");
			
			npc.moveTo(p.getLocation());
			npc.lookAt(p.getLocation());

			if (npc.getHumanNPC().getBukkitEntity().getLocation().distance(p.getLocation()) < 2) {
				p.sendMessage("NPC HIT YOU");
				npc.getHumanNPC().animateArmSwing();
				p.damage(1);
				System.out.println(p.getHealth());
			}

		} else {
			p.sendMessage("Left attack zone NPC aggro lost!");
			npc.unAggro();
		}

		// else if aggressor has left area stop attacking
	}

}
