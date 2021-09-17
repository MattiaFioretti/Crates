package it.matty.crate.crates.editor;

import it.matty.crate.crates.cratekey.Crate;
import it.matty.crate.crates.editor.object.CrateEditor;
import org.bukkit.entity.Player;

import java.util.Set;

public interface IEditorManager {

    Set<CrateEditor> getEditors();

    CrateEditor getEditor(Player player);

    void addEditor(Crate crate, Player player);

    void removeEditor(Player player);

    boolean isEditing(Player player);
}
