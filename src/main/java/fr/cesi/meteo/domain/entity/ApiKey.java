package fr.cesi.meteo.domain.entity;

import fr.cesi.divers.mysql.persist.annotation.Key;
import fr.cesi.divers.mysql.persist.annotation.KeyType;
import fr.cesi.divers.mysql.persist.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "api_keys")
public class ApiKey {

    @Key(keyType = KeyType.VARCHAR, notNull = true, length = 255)
    private String key;

}
