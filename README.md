# GLOSSY 0.1.0 (Fabric, Minecraft Java 26.3, client-side)

Included:
- Blue/cyan GUI theme: buttons, hotbar, hotbar selection, crosshair (resource overrides inside the mod)
- Cyan flame trail while sprinting
- Electric blue burst when you hit an entity
- Sparkles around you when holding an enchanted item
- Config file: .minecraft/config/glossy.json (sprintTrail, hitEffects, enchantSparkles, particleAmount 0-10)

Not included yet (planned): first-person animations, mob animations, lighting/shadows, water reflections,
animated menus, custom inventory screen, loading screen. These need rendering code and shaders.

Build: pushed to GitHub, the Actions tab builds build/libs/glossy-0.1.0.jar.
Install: put glossy-0.1.0.jar and Fabric API (for 26.3) in .minecraft/mods, run with Fabric Loader.
