package Repository;

import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Roles.Student;
import Validator.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class StudentRepository extends DBRepository<Student> {
    private Long disciplina;

    public StudentRepository(Validator validator, Properties props) {
        super(validator,props);
        this.tableName = "students";
    }

    @Override
    protected Student parse(ResultSet data) throws SQLException {
        Student student = new Student(data.getLong("id"), data.getInt("grupa"), data.getInt("an"));
        return student;
    }

    @Override
    public Student save(Student entity) throws ValidationException, IllegalArgumentException, SQLException {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("insert into " + tableName + "(id,grupa,an)" + " values(?,?,?);")) {
            statement.setInt(1,entity.getId().intValue());
            statement.setInt(2,entity.getGrupa());
            statement.setInt(3,entity.getAn());
            int afected=statement.executeUpdate();
            if(afected>0)
                return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Student update(Student entity) throws IllegalArgumentException, ValidationException, SQLException {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("update " + tableName + " set  grupa=?,an=? where id=?")) {
            statement.setInt(3,entity.getId().intValue());
            statement.setInt(1,entity.getGrupa());
            statement.setInt(2,entity.getAn());
            int afected=statement.executeUpdate();
            if(afected>0)
                return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
