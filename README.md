WAILAPlugins
============

Miscellaneous WAILA plugins for various mods. Requires [EnderCore](http://ci.tterrag.com/job/EnderCore/).

###Currently Supported:

- IC2 Crops
  - Name & Stats
  - Stage, Growth, Growability, Harvestability
  - Environment Info
  
- GT5u
   - Transformer mode (Step Up/Step Down)
   - Current side: input/output & EU
   - Multiblock info

- Blood Magic (Ported from ImLookingAtBlood, by Pokefenn)
  - Capacity, LP, and tier of altars
  - Progress of the altar's current craft
  - Current recipe result of Chemistry set
  - Progress of chemistry set
  - Owner of master ritual stone
  - Current ritual of master ritual stone
  - Current block/item in teleposer

- Forestry
  - Sapling genomes
  - Pollinated leaves info
  - Current bees inside apiaries/beehouses/alvearies (and genome info)
  - Apiary errors (No flowers, no drone, etc.)
  - Progress percentage of current breed
  - RF in of engines
  - Heat in engines
  
- Pam's Harvestcraft
  - Adds a growth percentage to tree fruits
  
- Railcraft
  - Fluid inside machines
  - Locomotive information (steam, heat, charge)
  - Heat information on coal burning machines
  - Whether a multiblock is formed or not
  - Amount of charge in blocks
  - Engine production rate
    
- RedLogic
  - Ported overlay rendering from the Project:Red plugin in WAILA
  - Info for certain gates such as Timer, Repeater, etc.
  - Strength of red alloy wires

###Contributing

Simply clone this project and run the usual gradle tasks, and all depended on mods will be automatically downloaded and added to your classpath. Easy!

If you are wanting to add a new plugin, add the mod yourself manually, and once you have finished I will update the server's libs.
