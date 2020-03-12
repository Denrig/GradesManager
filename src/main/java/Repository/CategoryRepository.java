package Repository;

import Entity.Category;
import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Validator.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CategoryRepository extends DBRepository<Category> {
    private Long disciplina;

    public CategoryRepository(Validator validator,Properties props,Long disciplina) {
        super(validator,props);
        this.tableName = "category";
        this.disciplina = disciplina;
    }

    public Long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Long disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    protected Category parse(ResultSet data) throws SQLException {
        return new Category(data.getLong("id"), data.getString("nume"), data.getInt("procent"), data.getLong("disciplina"));
    }

    @Override
    public Category save(Category entity) throws ValidationException, IllegalArgumentException {
        logger.traceEntry();
        validator.validate(entity);
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("insert into " + tableName + "(id,nume,procent,disciplina)" + " values(?,?,?,?);")) {
            statement.setInt(1,entity.getId().intValue());
            statement.setString(2,entity.getName());
            statement.setInt(3,entity.getPercentage());
            statement.setInt(4,entity.getDisciplinaID().intValue());
           int afected=statement.executeUpdate();
           if(afected>0)
           {logger.info("Saved with succes "+getClass());
               return null;}
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();

        }
        logger.traceExit();
        return entity;

    }

    @Override
    public Category update(Category entity) throws IllegalArgumentException, ValidationException {
        logger.traceEntry();
        validator.validate(entity);
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("update " + tableName + " set  nume=?,procent=?, disciplina=? where id=?")) {
            statement.setInt(4,entity.getId().intValue());
            statement.setString(1,entity.getName());
            statement.setInt(2,entity.getPercentage());
            statement.setInt(3,entity.getDisciplinaID().intValue());
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
    public Iterable<Category> findAll() {
        return StreamSupport.stream(super.findAll().spliterator(),false).filter(x->x.getDisciplinaID()==disciplina).collect(Collectors.toList());
    }
}

