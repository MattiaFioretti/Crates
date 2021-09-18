package it.matty.crate.crates.editor;

import it.matty.crate.crates.crates.Crate;
import it.matty.crate.crates.editor.sessions.EditSession;
import org.bukkit.entity.Player;

import java.util.Set;

public interface EditorService {

    Set<EditSession> getEditors();

    EditSession getEditor(Player player);

    void addEditor(Crate crate, Player player);

    void removeEditor(Player player);

    boolean isEditing(Player player);
}
