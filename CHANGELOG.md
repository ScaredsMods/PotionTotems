## 2.0.0
- Renamed ```init``` package to ```registry```
- Removed ```ModResourceKeys``` class
- Renamed ```data.gen``` package to ```datagen```
- Reworked both infuser models
- Reworked infuser recipes
- New item: Infusion Core
- New block: Infuser Frame
- Added loottables for both infusers (Why weren't they here before?)

## 1.1.3
This update contains more technical changes than additions.
NOTE: The brewing recipes are subject to change in the next version (2.0.0) for minecraft 26.1 and more nerfs and changes may follow.
- Improvements to villager prices and offers
- Added a recipe for the advanced infuser (How did i forget this?)
- Added a new potion containing all positive effects
- Added a new potion containing all negative effects
- Added a builtin, automatically generated resourcepack for compatibility with other mods
  - ````ResourcepackBuilder```` is a class that can be used for automatic resourcepack generation. I am working on an public API or tutorial (still deciding).
- Rewrote some classes
- Added a general ````copyPotionContents()```` method called ```copyDataComponent()``` for other use-cases
- Renamed ```PotionTotemsMain``` to ```PotionTotems```
- Renamed classes that start with ```PT``` to ```Mod```
  - ```PTEnglishLanguageProvider``` &rarr; ```ModEnglishLanguageProvider```
  - ```PTItemModelProvider``` &rarr; ```ModItemModelProvider```
  - ```PTRecipesProvider``` &rarr; ```ModRecipesProvider```
  - ```PTBlockEntities``` &rarr; ```ModBlockEntities```
  - ```PTBlocks``` &rarr; ```ModBlocks```
  - ```PTConfigs``` &rarr; ```ModConfigs```
  - ```PTItems``` &rarr; ```ModItems```
  - ```PTMenuTypes``` &rarr; ```ModMenuTypes```
  - ```PTPotions``` &rarr; ```ModPotions```
  - ```PTVillagers``` &rarr; ```ModVillagers```
- Renamed block entity (and related) classes from ```BlockEntity{NameOfBlockEntity}``` to ```{NameOfBlockEntity}BlockEntity```
  - ```BlockEntityAdvancedInfuser``` &rarr; ```AdvancedInfuserBlockEntity```
  - ```BlockEntityBaseInfuser``` &rarr; ```AbstractTickingBlockEntity```
  - ```BlockEntityInfuser``` &rarr; ```InfuserBlockEntity```
  - ```BERAdvancedInfuser``` &rarr; ```AdvancedInfuserBER```
  - ```BERInfuser``` &rarr; ```InfuserBER```
- Removed ```lib``` package and moved its classes to their respective packages
- Removed unused methods from ```PotionUtils``` class
- Added a config purely used for development. DO. NOT. EDIT.
- Removed ```item.alchemy``` package and moved its class to the root ```item``` package
- Renamed ```ClientModEvents``` to ```ModClientEvents```
- Moved ```onFMLLoadComplete(FMLLoadCompleteEvent event)``` to ```ModClientEvents.java``` and removed ```ModEvents.java```
- Changed ```totem_base``` model parent from handheld to generated
- Nerfed the infusers in time since they were way too fast.
  - The regular infuser now takes 30 seconds to craft
  - The advanced infuser now takes 60 seconds (= 1 minute) to craft
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