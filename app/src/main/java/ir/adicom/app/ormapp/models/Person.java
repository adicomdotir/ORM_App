package ir.adicom.app.ormapp.models;

import com.j256.ormlite.field.DatabaseField;

/**
 *
 * Created by adicom on 6/7/17.
 */

public class Person {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String firstName;
    @DatabaseField
    private String lastName;

    public Person() {}
}
