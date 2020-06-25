package Bean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
//@Auteur:ZARBAG
//Classe pour la relation entre la BD
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    String columnName();
}