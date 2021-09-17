package it.matty.crate.crates.editor.object;

import it.matty.crate.crates.cratekey.Crate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor @Getter @Setter
public class CrateEditor {
    private UUID uuid;
    private Crate crate;
}
