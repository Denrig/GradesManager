package Repository;

import Entity.Semester;
import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Validator.Validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class SemesterRepository extends DBRepository<Semester> {
    public SemesterRepository(Validator validator, Properties props) {
        super(validator,props);
        this.tableName = "semestru";
    }

    @Override
    protected Semester parse(ResultSet data) throws SQLException {
        Semester semester = new Semester(data.getLong("id"), data.getInt("anuni"), data.getInt("semester")
                , data.getDate("startsemester").toLocalDate(), data.getDate("endsemester").toLocalDate(), data.getDate("startholyday").toLocalDate(),
                data.getDate("endholyday").toLocalDate());
        return semester;

    }


    @Override
    public Semester save(Semester entity) throws ValidationException, IllegalArgumentException, SQLException {
        return null;
    }

    @Override
    public Semester update(Semester entity) throws IllegalArgumentException, ValidationException, SQLException {
        return null;
    }
}

