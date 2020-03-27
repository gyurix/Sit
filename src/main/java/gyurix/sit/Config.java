package gyurix.sit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GyuriX on 2017. 06. 17.
 */
public class Config {
  public static Click click;
  public static double groundHeight;
  public static Heal heal;
  public static int version;

  public static class Heal {
    public static int checkRate;
    public static boolean enabled;
    public static HashMap<String, Double> groups = new HashMap<>();

    public static double getAmount(Player p) {
      double max = 0;
      for (Map.Entry<String, Double> e : groups.entrySet())
        if (max < e.getValue() && p.hasPermission("sit.heal." + e.getKey()))
          max = e.getValue();
      return max;
    }
  }

  public static class Click {
    public static boolean blockUpperSlabs = true;
    public static HashMap<Material, Double> blocks;
    public static boolean enabled;
  }
}
