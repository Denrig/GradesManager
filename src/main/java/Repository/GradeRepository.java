package Repository;

import Entity.Grade;
import Entity.Student;
import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Validator.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class GradeRepository extends DBRepository<Grade> {
    private Long disciplina;

    public GradeRepository(Validator validator, Properties props,Long disciplina) {
        super(validator,props);
        this.tableName = "nota";
        this.disciplina = disciplina;
    }

    public Long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Long disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    protected Grade parse(ResultSet data) throws SQLException {
        logger.traceEntry();
        Grade grade = new Grade(data.getLong("id"), data.getLong("studentid"), data.getLong("category"), data.getDouble("nota"),
                data.getDate("incarcata").toLocalDate());
        logger.traceExit();
        return grade;

    }

    @Override
    public Grade save(Grade entity) throws ValidationException, IllegalArgumentException, SQLException {
        logger.traceEntry();
        validator.validate(entity);
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("insert into " + tableName + "(id,studentid,category,nota,incarcata)" + " values(?,?,?,?,?);")) {
            statement.setInt(1,entity.getId().intValue());
            statement.setInt(2,entity.getStudentID().intValue());
            statement.setInt(3,entity.getCategory().intValue());
            statement.setDouble(4,entity.getGrade());
            statement.setDate(5,Date.valueOf(entity.getUploaded()));
            int afected=statement.executeUpdate();
            if(afected>0)
            {logger.info("Saved with succes "+getClass());
                return null;}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.traceExit();
        return entity;
    }

    @Override
    public Grade update(Grade entity) throws IllegalArgumentException, ValidationException, SQLException {
        logger.traceEntry();
        validator.validate(entity);
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("update " + tableName + " set  nota=?,studentid=?, category=?,incarcata=? where id=?")) {
            statement.setInt(5,entity.getId().intValue());
            statement.setDouble(1,entity.getGrade());
            statement.setInt(2,entity.getStudentID().intValue());
            statement.setInt(3,entity.getCategory().intValue());
            statement.setDate(4,Date.valueOf(entity.getUploaded()));
            int afected=statement.executeUpdate();
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
    public Iterable<Grade> findAll() {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("select * from nota inner join category on nota.category=category.id where category.disciplina=?")) {
            statement.setInt(1,disciplina.intValue());
            ResultSet afected=statement.executeQuery();
            ArrayList<Grade> list=new ArrayList<>();
            while (afected.next()){
                list.add(parse(afected));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.traceExit();
        return super.findAll();
    }
}
