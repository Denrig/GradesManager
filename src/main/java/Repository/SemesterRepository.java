package Repository;

import Entity.Semester;
import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Validator.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
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
        logger.traceEntry();
        Semester semester = new Semester(data.getLong("id"), data.getInt("anuni"), data.getInt("semester")
                , data.getDate("startsemester").toLocalDate(), data.getDate("endsemester").toLocalDate(), data.getDate("startholyday").toLocalDate(),
                data.getDate("endholyday").toLocalDate());
        logger.traceExit();
        return semester;

    }


    @Override
    public Semester save(Semester entity) throws ValidationException, IllegalArgumentException, SQLException {
        logger.traceEntry();
        validator.validate(entity);
        try(PreparedStatement statement=jbdcUtils.getConnection().prepareStatement("insert into semestru" +
                "(id, anuni, semester, startsemester, endsemester, startholyday, endholyday) values (?,?,?,?,?,?,?)")){
            statement.setInt(1,entity.getId().intValue());
            statement.setInt(2,entity.getyear());
            statement.setInt(3,entity.getsemester());
            statement.setDate(4, Date.valueOf(entity.getStartSemester()));
            statement.setDate(5,Date.valueOf(entity.getEndSemester()));
            statement.setDate(6,Date.valueOf(entity.getBeginHolyday()));
            statement.setDate(7,Date.valueOf(entity.getEndHolyday()));
            if(statement.executeUpdate()>0)
            {logger.info("Saved with succes "+getClass());
                return null;}
            logger.traceExit();
            return entity;
        }
    }

    @Override
    public Semester update(Semester entity) throws IllegalArgumentException, ValidationException {
        logger.traceEntry();
        validator.validate(entity);
        try(PreparedStatement statement=jbdcUtils.getConnection().prepareStatement("update semestru set anuni=?,semester=?,startsemester=?,endsemester=?,startholyday=?,endholyday=? where id=?")){
            statement.setInt(7,entity.getId().intValue());
            statement.setInt(1,entity.getyear());
            statement.setInt(2,entity.getsemester());
            statement.setDate(3, Date.valueOf(entity.getStartSemester()));
            statement.setDate(4,Date.valueOf(entity.getEndSemester()));
            statement.setDate(5,Date.valueOf(entity.getBeginHolyday()));
            statement.setDate(6,Date.valueOf(entity.getEndHolyday()));
            if(statement.executeUpdate()>0)
            {logger.info("Updated with succes "+getClass());
                return null;}

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }logger.traceExit();
        return entity;
    }
}

