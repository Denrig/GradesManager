package Repository;

import Entity.Discipline;
import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Validator.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DisciplinaRepository extends DBRepository<Discipline> {

    public DisciplinaRepository(Validator validator,Properties props) {
        super(validator,props);
        this.tableName = "disciplina";
    }

    @Override
    protected Discipline parse(ResultSet data) throws SQLException {
        return new Discipline(data.getLong("id"), data.getString("profesor"), data.getLong("semester"), data.getString("nume"));
    }






    @Override
    public Discipline save(Discipline entity) throws ValidationException, IllegalArgumentException, SQLException {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("insert into " + tableName + "(id,nume,profesor,semester)" + " values(?,?,?,?);")) {
            statement.setInt(1,entity.getId().intValue());
            statement.setString(2,entity.getname());
            statement.setString(3,entity.getProfesor());
            statement.setInt(4,entity.getSemester().intValue());
            int afected=statement.executeUpdate();
            if(afected>0)
                return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Discipline update(Discipline entity) throws IllegalArgumentException, ValidationException, SQLException {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("update " + tableName + " set  nume=?,profesor=?, semester=? where id=?")) {
            statement.setInt(4,entity.getId().intValue());
            statement.setString(1,entity.getname());
            statement.setString(2,entity.getProfesor());
            statement.setInt(3,entity.getSemester().intValue());
            int afected=statement.executeUpdate();
            if(afected>0)
                return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
