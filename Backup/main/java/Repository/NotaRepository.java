package Repository;

import Entity.Grade;
import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Validator.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class NotaRepository extends DBRepository<Grade> {
    private Long disciplina;

    public NotaRepository(Validator validator, Properties props) {
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
        Grade grade = new Grade(data.getLong("id"), data.getLong("studentid"), data.getLong("category"), data.getDouble("nota"),
                data.getDate("incarcata").toLocalDate());
        return grade;
    }

    @Override
    public Grade save(Grade entity) throws ValidationException, IllegalArgumentException, SQLException {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("insert into " + tableName + "(id,studentid,category,nota,incarcata)" + " values(?,?,?,?,?);")) {
            statement.setInt(1,entity.getId().intValue());
            statement.setInt(2,entity.getStudentID().intValue());
            statement.setInt(3,entity.getCategory().intValue());
            statement.setDouble(4,entity.getGrade());
            statement.setDate(5,Date.valueOf(entity.getuploaded()));
            int afected=statement.executeUpdate();
            if(afected>0)
                return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Grade update(Grade entity) throws IllegalArgumentException, ValidationException, SQLException {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("update " + tableName + " set  nota=?,studentid=?, category=?,incarcata=? where id=?")) {
            statement.setInt(5,entity.getId().intValue());
            statement.setDouble(1,entity.getGrade());
            statement.setInt(2,entity.getStudentID().intValue());
            statement.setInt(3,entity.getCategory().intValue());
            statement.setDate(4,Date.valueOf(entity.getuploaded()));
            int afected=statement.executeUpdate();
            if(afected>0)
                return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
