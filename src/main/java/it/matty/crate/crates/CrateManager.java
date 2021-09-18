package it.matty.crate.crates;

import it.matty.crate.CratePlugin;
import it.matty.crate.config.objects.IConfigFile;
import it.matty.crate.crates.crates.Crate;
import it.matty.crate.crates.crates.impl.DefaultCrate;
import it.matty.crate.crates.editor.EditorServiceImpl;
import it.matty.crate.crates.editor.EditorService;
import it.matty.crate.crates.rewards.CrateReward;
import it.matty.crate.crates.rewards.Reward;
import it.matty.crate.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CrateManager implements ICrateManager {
    @Getter
    private final Set<Crate> crates = new HashSet<>();
    @Getter
    private final EditorService editorManager;

    public CrateManager() {
        this.editorManager = new EditorServiceImpl();
    }

    @Override
    public Crate getCrate(String name) {
        for (Crate crate : crates) {
            if (crate.getName().equalsIgnoreCase(name))
                return crate;
        }
        return null;
    }

    @Override
    public Crate getCrate(ItemStack itemStack) {
        for (Crate crate : crates) {
            if (crate.getCrate().isSimilar(itemStack)) {
                if (crate.getCrate() == null) continue;
                return crate;
            }
        }
        return null;
    }

    @Override
    public void removeCrate(Crate crate) {
        crates.remove(crate);

        Bukkit.getScheduler().runTaskAsynchronously(CratePlugin.getPlugin(), () -> {
            IConfigFile file = CratePlugin.getPlugin().getFileManager().getFile("crates");

            file.getConfig().set(crate.getName(), null);

            try {
                file.saveAndReload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void addCrate(Crate crate) {
        crates.add(crate);
    }

    @Override
    public void saveCrate(Crate crate) {
        Bukkit.getScheduler().runTaskAsynchronously(CratePlugin.getPlugin(), () -> {
            IConfigFile file = CratePlugin.getPlugin().getFileManager().getFile("crates");

            file.getConfig().set(crate.getName(), null);

            file.getConfig().set(crate.getName() + ".item.material", crate.getCrate().getType().name());
            file.getConfig().set(crate.getName() + ".item.name", crate.getCrate().getItemMeta().getDisplayName());
            file.getConfig().set(crate.getName() + ".item.lore", crate.getCrate().getItemMeta().getLore());

            int i = 0;
            for (Reward reward : crate.getItems()) {
                file.getConfig().set(crate.getName() + ".rewards." + i + ".material", reward.getItem().getType().name());
                file.getConfig().set(crate.getName() + ".rewards." + i + ".percentage", reward.getChance());
                i++;
            }

            try {
                file.saveAndReload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void loadCrates() {
        IConfigFile file = CratePlugin.getPlugin().getFileManager().getFile("crates");

        for (String crates : file.getConfig().getKeys(false)) {
            Crate crate = new DefaultCrate(crates, new ItemBuilder(
                    Material.valueOf(file.getConfig().getString(crates + ".item.material")))
                    .setName(file.getConfig().getString(crates + ".item.name"))
                    .setLore(file.getConfig().getStringList(crates + ".item.lore")).build(), new HashSet<>());

            for (String rewards : file.getConfig().getConfigurationSection(crates + ".rewards").getKeys(false)) {
                crate.addItem(new CrateReward(new ItemStack(
                        Material.valueOf(file.getConfig().getString(crates + ".rewards." + rewards + ".material"))),
                        file.getConfig().getDouble(crates + ".rewards." + rewards + ".percentage")));
            }
            getCrates().add(crate);
        }
    }
}
