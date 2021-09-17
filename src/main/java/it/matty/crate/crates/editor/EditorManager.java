package it.matty.crate.crates.editor;

import it.matty.crate.crates.cratekey.Crate;
import it.matty.crate.crates.editor.object.CrateEditor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class EditorManager implements IEditorManager {
    @Getter private final Set<CrateEditor> editors = new HashSet<>();

    @Override
    public CrateEditor getEditor(Player player) {
        for(CrateEditor editor : editors) {
            if(player.getUniqueId().equals(editor.getUuid()))
                return editor;
        }
        return null;
    }

    @Override
    public boolean isEditing(Player player) {
        return getEditor(player) != null;
    }

    @Override
    public void addEditor(Crate crate, Player player) {
        editors.add(new CrateEditor(player.getUniqueId(), crate));
    }

    @Override
    public void removeEditor(Player player) {
        editors.remove(getEditor(player));
    }
}
