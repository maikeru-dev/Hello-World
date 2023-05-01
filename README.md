# Hello-World
This is a test Minecraft plugin I originally started on git local, but decided to push to a remote on GitHub.
This project was obviously coded with Java, because my University uses that mostly, and it seems to have worked well using the Paper Plugin API.
It is a slight passion project, despite being called Hello-World, as it provides several standard server features.
If the code is bad... excuse is that I learned how oop works only in January '23 -> March '23.

# Important highlights:
This code compiles to a specific place and I recommend you check the maven configuration file before trying to compile this thing.
I sometimes use Exceptions to do control-flow, however performance-hits are mitigated by the lack of requests and a custom exception with a overriden null 'fillInStack' method. 

# What works at the moment?
- [x] Hologram basic functionality (Spawn floating text, kill floating text)
- [ ] Advanced Hologram functionality (Edit text -> add text, remove text)
- [ ] Extra Hologram functionality (Parse color and text information to pass to kyori's TextComponent) [could use mini message]
- [x] PM and Reply Command (Can send PM and reply to last PM messaged)
- [x] A flat file accessor class, works very well for accessing data inexcessively.
- [x] Hello World! command, just says Hello World to you.
- [x] Voteskip command for skipping the night, plus a couple messages
- [x] setDisplayName command, essentially sets a nickname for the executor with whatever they like
- [ ] Creative Command, it is meant to be a counterpart to the survival command and it is meant to strip you of your user data and gives you the creative gamemode.
- [ ] Survival command, the vice-versa, gives your backup user data and gives you the survival gamemode. (This is useful for development on a SMP or something.)
