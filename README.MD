# Sit

## Introduction

It's time to sit down, relax, and boost the regeneration speed. This tiny plugin contains all of that.

## Dependencies

The plugin depends on **[SpigotLib](https://www.spigotmc.org/resources/spigotlib.5925/)** from version 2.0.
So make sure to add it's latest version to your plugins folder.

## Contact

Before asking anything please read this description several times, and make sure you added all the dependencies to your server.

If you still have any question, you found a bug, or you need a custom plugin feel free to contact me on Discord (**gyurix#8413**).

When you see that the plugin is working as it should please do not forget to leave a review.

## Commands

- **/sit:** for sitting down to the ground

## Permissions
- **sit.use:** for using the **/sit** command
- **sit.heal.\<group\>:** for getting the healing effect of a configured healing group when sitting
- **sit.block.\<material\>:** for sitting to a block when right clicking to it.

**[List of valid materials](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)**

## Configuration

```yaml
#Config version, do not change it
version: 2
#Permission: sit.heal.<groupname>
heal:
  checkRate: 20
  groups:
    default: 1
    donor: 2
    vip: 3
  enabled: +
#Y level of the sitting
groundHeight: 0.3
click:
  blockUpperSlabs: +
  enabled: +
  #Y level of sitting on each block
  #Permission: sit.block.<blocktype>
  blocks:
    PURPUR_STAIRS: 1
    OAK_STAIRS: 1
    COBBLESTONE_STAIRS: 1
    BRICK_STAIRS: 1
    STONE_BRICK_STAIRS: 1
    NETHER_BRICK_STAIRS: 1
    SANDSTONE_STAIRS: 1
    SPRUCE_STAIRS: 1
    BIRCH_STAIRS: 1
    JUNGLE_STAIRS: 1
    QUARTZ_STAIRS: 1
    ACACIA_STAIRS: 1
    DARK_OAK_STAIRS: 1
    PRISMARINE_STAIRS: 1
    PRISMARINE_BRICK_STAIRS: 1
    DARK_PRISMARINE_STAIRS: 1
    RED_SANDSTONE_STAIRS: 1
    OAK_SLAB: 0.8
    SPRUCE_SLAB: 0.8
    BIRCH_SLAB: 0.8
    JUNGLE_SLAB: 0.8
    ACACIA_SLAB: 0.8
    DARK_OAK_SLAB: 0.8
    STONE_SLAB: 0.8
    SANDSTONE_SLAB: 0.8
    PETRIFIED_OAK_SLAB: 0.8
    COBBLESTONE_SLAB: 0.8
    BRICK_SLAB: 0.8
    STONE_BRICK_SLAB: 0.8
    NETHER_BRICK_SLAB: 0.8
    QUARTZ_SLAB: 0.8
    RED_SANDSTONE_SLAB: 0.8
    PURPUR_SLAB: 0.8
    PRISMARINE_SLAB: 0.8
    PRISMARINE_BRICK_SLAB: 0.8
    DARK_PRISMARINE_SLAB: 0.8
```

## License

- You are **ALLOWED** to modify the plugins source code only for your own server/network.

- You are **NOT ALLOWED** to share the plugin or it's any modified versions,
you are only allowed to share the URL of this webpage​