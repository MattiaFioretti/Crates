package it.matty.crate.crates.editor.sessions;

import it.matty.crate.crates.crates.Crate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class EditSession {
    private UUID uuid;
    private Crate crate;
}
