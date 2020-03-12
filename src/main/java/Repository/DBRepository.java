package Repository;

import Entity.Entity;
import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Utils.JDBCutils;
import Validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public abstract class DBRepository<T extends Entity<Long>> implements CrudRepository<Long, T> {
    protected static final Logger logger= (Logger) LogManager.getLogger();
    protected String tableName = null;
    protected Validator<T> validator;
    protected static JDBCutils jbdcUtils=null;

    public DBRepository(Validator validator, Properties props) {
        logger.traceEntry();
            this.validator=validator;
            jbdcUtils=new JDBCutils(props);
            logger.traceExit(this);
    }
    public Properties getProps(){
        return jbdcUtils.getJdbcProps();
    }
    @Override
    public T findOne(Long id) throws SQLException {
        logger.traceEntry();
        if (id == null)
            throw new IllegalArgumentException("Entitate nulla");

        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("SELECT * from " + tableName + " where id=?")) {
            statement.setInt(1,id.intValue());
            ResultSet st = statement.executeQuery();
            st.next();
            T ent=parse(st);
            logger.traceExit("Entity:"+ent);
            return ent;
        }

    }

    @Override
    public Iterable<T> findAll() {
        logger.traceEntry();
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("select * from "+tableName)) {
            ArrayList<T> list = new ArrayList<>();
            ResultSet data = statement.executeQuery();
            while (data.next())
                list.add(parse(data));
            logger.traceExit("Size of list:"+list.size());
            return list;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T delete(Long id) throws ValidationException, IllegalArgumentException {
        if (id == null)
            throw new IllegalArgumentException("Null ID");
        logger.traceEntry();
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("delete from " + tableName + " where id=?")) {
            statement.setInt(1,id.intValue());
            T ent = findOne(id);
            int afected = statement.executeUpdate();
            if (afected != 0)
            {logger.info("Deleted whit succes");
                return ent;}
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public Long getLastID() throws SQLException {
        logger.traceEntry();
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("select id from " + tableName + " where id>0 order by id asc")) {
            ResultSet data = statement.executeQuery();
            Long i = 1l;
            while (data.next()) {
                if (data.getLong("id") != i)
                    return i;
                i++;
            }
            logger.traceExit();
            return i;
        }

    }

    protected abstract T parse(ResultSet data) throws SQLException;

}
