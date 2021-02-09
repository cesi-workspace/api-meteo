package fr.cesi.meteo.domain.model;

import fr.cesi.mysql.Values;
import fr.cesi.mysql.persist.Persist;
import fr.cesi.mysql.persist.annotation.Key;
import fr.cesi.mysql.persist.annotation.KeyType;
import fr.cesi.mysql.persist.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "datas")
public class Data extends Persist {

    @Key(keyType = KeyType.BIGINT, notNull = true)
    private long createdAt = Values.NULL_INTEGER;

    @Key(keyType = KeyType.INT, notNull = true)
    private int temperature = Values.NULL_INTEGER;

    @Key(keyType = KeyType.INT, notNull = true, unsigned = true)
    private long humidity = Values.NULL_INTEGER;

}

