# RetroGDX

The idea behind RetroGDX is to provide a library to read old gameNode formats into data containers, which then can be used for either remakes or custom projects where the developers are lacking the skill of making assets. This approach is preferred over extracting gameNode assets, so RetroGDX based projects do not need to ship any copyrighted assets. Common use cases would be downloading and using the demo version files, or requiring users to buy the full gameNode for the assets.

# Concept

While RetroGDX is based upon libGDX, you are not forced to use libGDX at all. The core part of RetroGDX are the format readers which do not rely on any libGDX class and can simply be used in any Java project. I however decided to use libGDX as a base, because it allows me to spend my time on the project instead of writing renderers for the 2d and 3d content, the ui, whatever. These classes will read the formats out of SmartByteBuffer objects. The major advantage is that its contents will only be parsed when you want to parse it, which saves a lot of unnecessary cpu load and allows to load assets like individual files.

Upon that the default retroGDX application core consists of a simple tree containing all the assets and a preview area. The tree is filled by scanning the folder "games" for supported games. If a gameNode gets detected, the gameNode class may continue to add sub-nodes for assets or containers, populating the tree even further. Every node has a preview method, which allows to either preview the asset, or show custom content.

# Status

## Blood & Magic
- STF container - done
- WAV sound - done
- PAL palette - done
- SQB text - done
- ANI animation - palette mapping missing
- TLB tileset - palette mapping missing
- HMP music - completely missing
- MIF map - completely missing
- BNK soundbank - completely missing
- FNT font - completely missing

## Dominion: Storm over Gift 3
- RDF container - done
- WAV sound - done
- 000/BIN container - the BIN file has some unidentified chunks left
- SPR sprite - palette missing
- TL2 tileset - completely missing
- WXM/SDC map - completely missing
- ??? unk - completely missing (the unk format in BIN)
- ??? unk - completely missing (filetype 14-20 in 000)

## Dune II: The Building of a Dynasty
- PAK container - done
- INI scenario - done
- SHP spritesheet - done
- WSA animation - done
- VOC sound - done
- PAL palette - done
- ICN tileset - done
- CPS image - done
- TLB unk - usage needs to be determined before being verified as done
- MAP unk - completely missing (related to the tileset)
- FNT font - completely missing
- EMC script - completely missing
- ENG/FRE/GET translations - completely missing
- ADL/C55/XMI/PCS/TAN/DRV/ADV music - completely missing

## Earth 2140
- WD container - done
- TXT text - done
- INI text - done
- FLC animation - done
- PAL palette - done
- DAT image - done
- MIX spritesheet - done
- SMP sound - AudioFormat must be verified
- DAT map - completely missing
- KER/PCX font - completely missing
- MIX unk - completely missing (MIXMAX)

## Mayday: Conflict Earth
- SPR sprite - done
- PAL palette - done
- ART container - missing chunk at the beginning
- MAP map - completely missing

## State of War + State of War: Warmonger
- DATA/INFO container - done
- PS6 spritesheet - done
- PO1 unk - completely missing
- PO2 unk - completely missing
- MSK unk - completely missing
- SHA unk - completely missing
- EDT/MAP/SRF/SRFS/TIL/TMI map - completely missing

## War Wind + War Wind II: Human Onslaught
- WAV sound - done
- RES container - almost done, 000 which contains the gameNode strings, crashes
- D3GR spritesheet - palette missing
- PAL pal - implementation wrong
- DAT map - completely missing
- LVL map - completely missing

## Warcraft: Orcs & Humans
- WAR container - done
- FLC animation - done
- WAV sound - done
- VOC sound - done
- PAL palette - done
- TXT text - done
- IMG image - done
- CUR cursor - done
- SPR spritesheet - done
- TILE/TILEPART tileset - done
- XMI music - completely missing
- MAP map - completely missing
- GUI gui - completely missing

## Warcraft II: Battle.net Edition
- MPQ container - parsing done, file decryption missing
