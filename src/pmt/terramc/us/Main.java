package pmt.terramc.us;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public final String prefix = "&b&lGlobe&3&lMC &7» ";

    @Override
    public void onEnable(){
        RepairTool repairTool = new RepairTool(this);
    }

    public void onDisable(){

    }
}
