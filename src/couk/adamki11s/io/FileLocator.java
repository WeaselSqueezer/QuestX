package couk.adamki11s.io;

import java.io.File;
import java.io.IOException;

public class FileLocator {
	
	public static final String root = "plugins" + File.separator + "QuestX",
	config_root = root + File.separator + "Configuration",
	data_root = root + File.separator + "Data",
	npc_data_root = data_root + File.separator + "NPCs",
	
	dlgFile = "dialogue.dlg", propertyFile = "properties.txt", taskScript = "task.qxs",questScript = "quest_link.qxs";
	
	public static File getNPCRootDir(String npcName){
		return new File(npc_data_root + File.separator + npcName);
	}
	
	public static File getNPCDlgFile(String npcName){
		return new File(npc_data_root + File.separator + npcName + File.separator + dlgFile);
	}
	
	public static boolean doesNPCDlgFileExist(String npcName){
		return new File(npc_data_root + File.separator + npcName + File.separator + dlgFile).exists();
	}
	
	public static File getNPCPropertiesFile(String npcName){
		return new File(npc_data_root + File.separator + npcName + File.separator + propertyFile);
	}
	
	public static File getNPCTaskFile(String npcName){
		return new File(npc_data_root + File.separator + npcName + File.separator + taskScript);
	}
	
	public static File getNPCQuestLinkFile(String npcName){
		return new File(npc_data_root + File.separator + npcName + File.separator + questScript);
	}
	
	public static File getNPCTaskProgressionPlayerFile(String npcName, String playerName){
		return new File(npc_data_root + File.separator + npcName + File.separator + "Progression" + File.separator + playerName + ".prog");
	}
	
	public static void createPlayerNPCProgressionFile(String npcName, String playerName){
		try {
			new File(npc_data_root + File.separator + npcName + File.separator + "Progression" + File.separator + playerName + ".prog").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File getNPCFixedSpawnsFile(){
		return new File(data_root + File.separator + "fixed_spawns.qxs");
	}

}
