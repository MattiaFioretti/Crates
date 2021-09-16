package it.matty.crate.database.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum Tables {
    PLAYERS("CREATE TABLE IF NOT EXISTS players(id INT(11) NOT NULL AUTO_INCREMENT, UUID VARCHAR(255) NOT NULL, crates INT(11) NOT NULL DEFAULT 0, PRIMARY KEY (id)) ENGINE=InnoDB;");

    String table;
}
