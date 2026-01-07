## 1.1.3
This update contains more technical changes than additions
- Added a builtin, automatically generated resourcepack for compatibility with other mods
  - ````ResourcepackBuilder```` is a class that can be used for automatic resourcepack generation. I am working on an public API or tutorial (still deciding).
- Rewrote some classes
- Renamed ```PotionTotemsMain``` to ```PotionTotems```
- Added a general ````copyPotionContents()```` method called ```copyDataComponent()``` for other use-cases

## 1.1.2
- Fixed a mistake in translation
- Added compatibility for Vampirism
## 1.1.0
- Added an advanced infuser block
- Added a villager with predefined potion (totems)
- Added a custom potion Aggression
- Potion Totems now requires Fzzy Config instead of regular NeoForge config system
- Readded vanilla effects to the totem.
- Removed occlusion on the regular infuser 
- Fixed the shape of the infuser model by adding voxel shapes
## 1.0.2
- Added a crafting recipe for the infuser
## 1.0.1
- Fixed resourcefullib modId in dependency
## 1.0.0 (Initial release)
- Empty