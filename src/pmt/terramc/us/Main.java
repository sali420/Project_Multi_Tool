package pmt.terramc.us;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        RepairTool repairTool = new RepairTool(this);
    }

    public void onDisable(){

    }
}
