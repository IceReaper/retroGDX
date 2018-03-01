# RetroGDX

The idea behind RetroGDX is to provide a library to read old game formats into data containers, which then can be used for either remakes or custom projects where the developers are lacking the skill of making assets. This approach is preferred over extracting game assets, so RetroGDX based projects do not need to ship any copyrighted assets. Common use cases would be downloading and using the demo version files, or requiring users to buy the full game for the assets.

# Concept

While RetroGDX is based upon libGDX, you are not forced to use libGDX at all. The core part of RetroGDX are the format readers which do not rely on any libGDX class and can simply be used in any Java project. I however decided to use libGDX as a base, because it allows me to spend my time on the project instead of writing renderers for the 2d and 3d content, the ui, whatever. These classes will read the formats out of SmartByteBuffer objects. The major advantage is that its contents will only be parsed when you want to parse it, which saves a lot of unnecessary cpu load and allows to load assets like individual files.

Upon that the default retroGDX application core consists of a simple tree containing all the assets and a preview area. The tree is filled by scanning the folder "games" for supported games. If a game gets detected, the game class may continue to add sub-nodes for assets or containers, populating the tree even further. Every node has a preview method, which allows to either preview the asset, or show custom content.
