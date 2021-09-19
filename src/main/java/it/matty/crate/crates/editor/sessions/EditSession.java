package it.matty.crate.crates.editor.sessions;

import it.matty.crate.crates.crates.Crate;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class EditSession {
    private final UUID uuid;
    private final Crate crate;
    private ItemStack item;
}
