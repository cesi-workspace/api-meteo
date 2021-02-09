package fr.cesi.meteo.domain.model;

import fr.cesi.mysql.persist.Persist;
import fr.cesi.mysql.persist.annotation.Key;
import fr.cesi.mysql.persist.annotation.KeyType;
import fr.cesi.mysql.persist.annotation.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Table(name = "datas")
@Builder
@Getter
@Setter
public class Data extends Persist {

    @Key(keyType = KeyType.BIGINT, notNull = true)
    private long createdAt;

    @Key(keyType = KeyType.INT, notNull = true)
    private int temperature;

    @Key(keyType = KeyType.INT, notNull = true, unsigned = true)
    private int humidity;


}
