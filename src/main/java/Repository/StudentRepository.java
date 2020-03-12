package Repository;

import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Entity.Student;
import Validator.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;


public class StudentRepository extends DBRepository<Student> {
   private Long disciplina;

    public StudentRepository(Validator validator, Properties props,Long disciplina) {
        super(validator,props);
        this.tableName = "students";
        this.disciplina=disciplina;
    }

    public Long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Long disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    protected Student parse(ResultSet data) throws SQLException {
        Student student = new Student(data.getLong("id"), data.getInt("grupa"), data.getInt("an"),data.getString("email"),data.getString("nume"));
        return student;
    }

    @Override
    public Student save(Student entity) throws ValidationException, IllegalArgumentException {
        logger.traceEntry();
        validator.validate(entity);
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("insert into " + tableName + "(id,grupa,an,nume,email)" + " values(?,?,?,?,?); insert into discipline_students values(?,?)")) {
            statement.setInt(1,entity.getId().intValue());
            statement.setInt(2,entity.getGrupa());
            statement.setInt(3,entity.getAn());
            statement.setString(4,entity.getNume());
            statement.setString(5,entity.getEmail());
            statement.setInt(6,entity.getId().intValue());
            statement.setInt(7,disciplina.intValue());
            int afected=statement.  executeUpdate();
            if(afected>0)
            {logger.info("Saved with succes "+getClass());
                return null;}
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }logger.traceExit();
        return entity;
    }

    @Override
    public Student update(Student entity) throws IllegalArgumentException, ValidationException {
        logger.traceEntry();
        validator.validate(entity);
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("update " + tableName + " set  grupa=?,an=?,nume=?,email=? where id=?")) {
            statement.setInt(5,entity.getId().intValue());
            statement.setInt(1,entity.getGrupa());
            statement.setInt(2,entity.getAn());
            statement.setString(3,entity.getNume());
            statement.setString(4,entity.getEmail());
            int afected=statement.executeUpdate();
            if(afected>0)
            {logger.info("Updated with succes "+getClass());
                return null;}
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }logger.traceExit();
        return entity;
    }

    @Override
    public Iterable<Student> findAll() {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("select * from students inner join discipline_students on students.id=discipline_students.student_id where discipline_students.disciplina_id=?")) {
            statement.setInt(1,disciplina.intValue());
            ResultSet afected=statement.executeQuery();
            ArrayList<Student> list=new ArrayList<>();
            while (afected.next()){
                list.add(parse(afected));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return super.findAll();
    }

    @Override
    public Student delete(Long id) throws ValidationException, IllegalArgumentException{

        try (PreparedStatement statement=jbdcUtils.getConnection().prepareStatement("delete from discipline_students where student_id=?")){
            statement.setInt(1,id.intValue());
            if(statement.executeUpdate()!=0)
                return super.delete(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
