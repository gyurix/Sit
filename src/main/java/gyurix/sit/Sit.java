package gyurix.sit;

import gyurix.configfile.ConfigFile;
import gyurix.protocol.Reflection;
import gyurix.sit.Config.Click;
import gyurix.sit.Config.Heal;
import gyurix.spigotlib.GlobalLangFile;
import gyurix.spigotlib.GlobalLangFile.PluginLang;
import gyurix.spigotlib.SU;
import gyurix.spigotutils.DualMap;
import gyurix.spigotutils.LocationData;
import gyurix.spigotutils.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.io.File;
import java.util.HashMap;

public class Sit extends JavaPlugin implements Listener {
  public static ConfigFile kf;
  public static PluginLang lang;
  public HashMap<String, ArmorStand> armorStands = new HashMap<>();
  public DualMap<String, LocationData> asbl = new DualMap<>();

  public void loadConfig() {
    SU.saveResources(this, "config.yml", "lang.yml");
    kf = new ConfigFile(new File(getDataFolder() + File.separator + "config.yml"));
    kf.data.deserialize(Config.class);
    if (Config.version < 2) {
      File to = new File(getDataFolder() + File.separator + "config.old.yml");
      to.delete();
      kf.file.renameTo(to);
      loadConfig();
    }
  }

  @EventHandler
  public void onBreak(BlockBreakEvent e) {
    String pln = asbl.removeValue(new LocationData(e.getBlock()));
    if (pln != null) {
      ArmorStand as = armorStands.get(pln);
      if (as != null)
        as.remove();
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      lang.msg(sender, "noconsole");
      return true;
    }
    Player plr = (Player) sender;
    if (!plr.hasPermission("sit.use"))
      return true;
    if (sit(plr.getLocation(), plr, Config.groundHeight))
      lang.msg(plr, "sit");
    return true;
  }

  public void onDisable() {
    for (ArmorStand as : armorStands.values())
      as.remove();
  }

  @Override
  public void onEnable() {
    loadConfig();
    lang = GlobalLangFile.loadLF("sit", getDataFolder() + File.separator + "lang.yml");
    if (Heal.enabled) {
      Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
        for (String s : armorStands.keySet()) {
          Player p = Bukkit.getPlayer(s);
          p.setHealth(Math.min(p.getHealth() + Heal.getAmount(p), p.getMaxHealth()));
        }
      }, Heal.checkRate, Heal.checkRate);
    }
    SU.pm.registerEvents(this, this);
  }

  @EventHandler
  public void onDismount(EntityDismountEvent e) {
    if (!(e.getEntity().getType() == EntityType.PLAYER && e.getDismounted().getType() == EntityType.ARMOR_STAND))
      return;
    Player plr = (Player) e.getEntity();
    ArmorStand as = armorStands.remove(plr.getName());
    if (as != null && as.getEntityId() == e.getDismounted().getEntityId()) {
      lang.msg(plr, "stand");
      as.remove();
      SU.sch.scheduleSyncDelayedTask(this, () -> plr.teleport(plr.getLocation().add(0, 1.5, 0)));
    }
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent e) {
    if (!Click.enabled || e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getItem() != null && e.getItem().getType() != Material.AIR)
      return;
    Player plr = e.getPlayer();
    Double d = Click.blocks.get(e.getClickedBlock().getType());
    Block b = e.getClickedBlock();
    if (Click.blockUpperSlabs && b.getBlockData() instanceof Slab) {
      Slab slab = (Slab) b.getBlockData();
      if (slab.getType() != Slab.Type.BOTTOM)
        return;
    }
    if (d != null && plr.hasPermission("sit.block." + e.getClickedBlock().getType()) && sit(e.getClickedBlock().getLocation().add(0.5, 0, 0.5), plr, d))
      lang.msg(plr, "sit.chair");
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    Player plr = e.getPlayer();
    ArmorStand as = armorStands.remove(plr.getName());
    if (as != null)
      as.remove();
  }

  public boolean sit(Location loc, Player plr, double height) {
    if (plr.getVehicle() != null || armorStands.containsKey(plr.getName())) {
      lang.msg(plr, "sit.already");
      return false;
    }
    ArmorStand as = (ArmorStand) plr.getWorld().spawnEntity(loc.subtract(0, 2 - height, 0), EntityType.ARMOR_STAND);
    as.setGravity(false);
    as.setVisible(false);
    if (Reflection.ver.isAbove(ServerVersion.v1_9))
      as.setInvulnerable(true);
    if (Reflection.ver.isAbove(ServerVersion.v1_13))
      as.addPassenger(plr);
    else
      as.setPassenger(plr);
    armorStands.put(plr.getName(), as);
    return true;
  }
}
