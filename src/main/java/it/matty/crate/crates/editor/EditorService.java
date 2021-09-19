package it.matty.crate.crates.editor;

import it.matty.crate.crates.crates.Crate;
import it.matty.crate.crates.editor.sessions.EditSession;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class EditorService implements IEditorService {
    @Getter
    private final Set<EditSession> editors = new HashSet<>();

    @Override
    public EditSession getEditor(Player player) {
        for(EditSession editor : editors) {
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
        editors.add(
                new EditSession(player.getUniqueId(), crate));
    }

    @Override
    public void removeEditor(Player player) {
        editors.remove(getEditor(player));
    }
}
