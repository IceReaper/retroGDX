# RetroGDX

The idea behind RetroGDX is to provide a library to read old game formats into data containers, which then can be used for either remakes or custom projects where the developers are lacking the skill of making assets. This approach is preferred over extracting game assets, so RetroGDX based projects do not need to ship any copyrighted assets. Common use cases would be downloading and using the demo version files, or requiring users to buy the full game for the assets.

# Concept

While RetroGDX is based upon libGDX, you are not forced to use libGDX at all. The core part of RetroGDX are the format readers which do not rely on any libGDX class and can simply be used in any Java project. I however decided to use libGDX as a base, because it allows me to spend my time on the project instead of writing renderers for the 2d and 3d content, the ui, whatever. These classes will read the formats out of SmartByteBuffer objects. The major advantage is that its contents will only be parsed when you want to parse it, which saves a lot of unnecessary cpu load and allows to load assets like individual files.

Upon that the default retroGDX application core consists of a simple tree containing all the assets and a preview area. The tree is filled by scanning the folder "games" for supported games. If a game gets detected, the game class may continue to add sub-nodes for assets or containers, populating the tree even further. Every node has a preview method, which allows to either preview the asset, or show custom content.

# Status

## Blood & Magic
- STF container - done
- PAL palette - done
- SQB text - done
- WAV sound - done
- ANI animation - palette mapping missing
- TLB tileset - palette mapping missing
- FNT font - completely missing
- HMP music - completely missing
- BNK soundbank - completely missing (may not contain useful assets)
- MIF map - completely missing (may not contain useful assets)

## Dominion: Storm over Gift 3
- BIN/000 container - the BIN file has some unidentified chunks left
- RDF container - done
- WAV sound - done
- SPR sprite - palette missing
- TL2 tileset - completely missing
- ??? unk - completely missing (the unk format in BIN)
- ??? unk - completely missing (filetype 14-20 in 000)
- WXM/SDC map - completely missing (may not contain useful assets)

## Dune II: The Building of a Dynasty
- PAK container - done
- CPS image - done
- ICN tileset - done
- INI scenario - done
- PAL palette - done
- SHP spritesheet - done
- VOC sound - done
- WSA animation - done
- TLB unk - incomplete (usage unknown)
- ADL/C55/XMI/PCS/TAN/DRV/ADV music - completely missing
- FNT font - completely missing
- MAP unk - completely missing (related to the tileset)
- EMC script - completely missing (may not contain useful assets)
- ENG/FRE/GET translations - completely missing (may not contain useful assets)

## Earth 2140
- WD container - done
- DAT image - done
- FLC animation - done
- INI text - done
- MIX spritesheet - done
- PAL palette - done
- TXT text - done
- SMP sound - AudioFormat must be verified (guessed values)
- KER/PCX font - completely missing
- MIX unk - completely missing (MIXMAX)
- DAT map - completely missing (may not contain useful assets)

## Future Cop: LAPD
- container - done
- bmp image - done
- wav sound - done
- msx sound - AudioFormat must be verified (guessed values)
- snds sound - AudioFormat must be verified (guessed values)
- act UNK - completely missing
- canm UNK - completely missing
- ctr UNK - completely missing
- dcs UNK - completely missing
- fnt UNK - completely missing
- fun UNK - completely missing
- net UNK - completely missing
- obj UNK - completely missing
- ptc UNK - completely missing
- pyr UNK - completely missing
- sac UNK - completely missing
- sfx UNK - completely missing
- shd UNK - completely missing
- til UNK - completely missing
- tos UNK - completely missing
- RPNS UNK - completely missing (may not be a file at all)

## Mayday: Conflict Earth
- PAL palette - done
- SPR sprite - done
- ART container - missing chunk at the beginning
- MAP map - completely missing (may not contain useful assets)

## State of War + State of War: Warmonger
- DATA/INFO container - done
- PS6 spritesheet - done
- MSK unk - completely missing
- PO1 unk - completely missing
- PO2 unk - completely missing
- SHA unk - completely missing
- EDT/MAP/SRF/SRFS/TIL/TMI map - completely missing

## War Wind + War Wind II: Human Onslaught
- WAV sound - done
- RES container - almost done, 000 which contains the game strings, crashes
- D3GR spritesheet - palette missing
- PAL pal - implementation wrong
- DAT map - completely missing (may not contain useful assets)
- LVL map - completely missing (may not contain useful assets)

## Warcraft: Orcs & Humans
- WAR container - done
- CUR cursor - done
- FLC animation - done
- IMG image - done
- PAL palette - done
- SPR spritesheet - done
- TILE/TILEPART tileset - done
- TXT text - done
- VOC sound - done
- WAV sound - done
- XMI music - completely missing
- GUI gui - completely missing (may not contain useful assets)
- MAP map - completely missing (may not contain useful assets)

## Warcraft II: Battle.net Edition
- MPQ container - parsing done, file decryption missing
