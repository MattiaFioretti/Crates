package it.matty.crate.crates;

import com.sun.istack.internal.Nullable;
import it.matty.crate.crates.crates.Crate;
import it.matty.crate.crates.editor.IEditorService;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public interface ICrateManager {

    IEditorService getEditorManager();

    Set<Crate> getCrates();

    @Nullable
    Crate getCrate(String name);

    @Nullable
    Crate getCrate(ItemStack itemStack);

    void addCrate(Crate crate);

    void saveCrate(Crate crate);

    void loadCrates();

    void removeCrate(Crate crate);
}
