package fr.cesi.meteo.domain.entity;

import fr.cesi.divers.mysql.persist.Persist;
import fr.cesi.divers.mysql.persist.annotation.Key;
import fr.cesi.divers.mysql.persist.annotation.KeyType;
import fr.cesi.divers.mysql.persist.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "datas")
public class Data extends Persist {

    @Key(keyType = KeyType.BIGINT, notNull = true)
    private long createdAt;

    @Key(keyType = KeyType.DOUBLE, notNull = true)
    private double temperature;

    @Key(keyType = KeyType.INT, notNull = true, unsigned = true)
    private long humidity;

}

