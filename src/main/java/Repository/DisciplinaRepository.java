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
        return new Discipline(data.getLong("id"), data.getLong("semester"), data.getString("nume"));
    }






    @Override
    public Discipline save(Discipline entity) throws ValidationException, IllegalArgumentException, SQLException {
        logger.traceEntry();
        validator.validate(entity);
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("insert into " + tableName + "(id,nume,semester)" + " values(?,?,?);")) {
            statement.setInt(1,entity.getId().intValue());
            statement.setString(2,entity.getName());
            statement.setLong(3,entity.getSemester());
            int afected=statement.executeUpdate();
            {logger.info("Saved with succes "+getClass());
                return null;}
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.traceExit();
        return entity;

    }

    @Override
    public Discipline update(Discipline entity) throws IllegalArgumentException, ValidationException, SQLException {
        logger.traceEntry();
        validator.validate(entity);
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("update " + tableName + " set  nume=?, semester=? where id=?")) {
            statement.setInt(3,entity.getId().intValue());
            statement.setString(1,entity.getName());
            statement.setInt(2,entity.getSemester().intValue());
            int afected=statement.executeUpdate();
            if(afected>0)
            {logger.info("Updated with succes "+getClass());
                return null;}
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.traceExit();
        return entity;
    }

    @Override
    public Discipline delete(Long id) throws ValidationException, IllegalArgumentException {
        try(PreparedStatement statement=jbdcUtils.getConnection().prepareStatement("delete from category where disciplina=?")) {
            statement.setInt(1,id.intValue());
            if(statement.executeUpdate()!=0)
                return super.delete(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
