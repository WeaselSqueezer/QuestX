package com.adamki11s.quests.requirements;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.adamki11s.bundle.LocaleBundle;
import com.adamki11s.quests.QuestManager;
import com.adamki11s.questx.QuestX;
import com.adamki11s.reputation.Reputation;
import com.adamki11s.reputation.ReputationManager;

public class QuestRequirements {

	private final String[] completedQuests;
	private final int minRep, maxRep, requiredGold;

	public QuestRequirements(String[] completedQuests, int minRep, int maxRep, int requiredGold) {
		this.completedQuests = completedQuests;
		this.minRep = minRep;
		this.maxRep = maxRep;
		this.requiredGold = requiredGold;
	}

	public boolean canPlayerUndertakeQuest(Player p) {
		if (QuestX.isEconomySupported()) {
			double balance = QuestX.economy.getBalance(p.getName());
			if (balance < this.requiredGold) {
				QuestX.logChat(p, ChatColor.RED + LocaleBundle.getString("you_need") + ChatColor.YELLOW + this.requiredGold + ChatColor.RED + LocaleBundle.getString("gold_req") + ChatColor.YELLOW + balance);
				return false;
			}
		}

		Reputation r = ReputationManager.getPlayerReputation(p.getName());
		int rep = r.getRep();
		if (minRep != 0 && maxRep != 0) {
			if (!(rep >= this.minRep && rep <= this.maxRep)) {
				QuestX.logChat(p, ChatColor.RED + LocaleBundle.getString("you_need_between") + ChatColor.YELLOW + minRep + " - " + maxRep + ChatColor.RED + LocaleBundle.getString("rep_req"));
				return false;
			}
		}

		boolean can = true;
		StringBuilder error = new StringBuilder().append(ChatColor.RED).append(LocaleBundle.getString("need_to_complete_quests")).append(ChatColor.RESET);

		if (!completedQuests[0].equalsIgnoreCase("0")) {

			for (String q : this.completedQuests) {
				if (!QuestManager.isQuestLoaded(q)) {
					QuestManager.loadQuest(q);
				}
				if (!QuestManager.hasPlayerCompletedQuest(q, p.getName())) {
					if (can) {
						can ^= true;
						// set can to false
					}
					error.append(q).append(", ");
				}
			}

		}

		if (!can) {
			QuestX.logChat(p, error.toString().substring(0, error.toString().length()));
			return false;
		}

		return true;

	}

}
