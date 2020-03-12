package Repository;

import Entity.Entity;
import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Utils.JDBCutils;
import Validator.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public abstract class DBRepository<T extends Entity<Long>> implements CrudRepository<Long, T> {
    protected String tableName = null;
    protected Validator<T> validator;
    protected static JDBCutils jbdcUtils=null;

    public DBRepository(Validator validator, Properties props) {
            this.validator=validator;
            jbdcUtils=new JDBCutils(props);
    }

    @Override
    public T findOne(Long id) throws SQLException {
        if (id == null)
            throw new IllegalArgumentException("Entitate nulla");

        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("SELECT * from " + tableName + " where id=?")) {

            ResultSet st = statement.executeQuery();
            st.next();
            return parse(st);
        }

    }

    @Override
    public Iterable<T> findAll() {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("select * from "+tableName)) {
            ArrayList<T> list = new ArrayList<>();
            ResultSet data = statement.executeQuery();
            while (data.next())
                list.add(parse(data));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T delete(Long id) throws ValidationException, IllegalArgumentException, SQLException {
        if (id == null)
            throw new IllegalArgumentException("Null ID");

        T ent = findOne(id);
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("delete from " + tableName + " where id=?")) {
            statement.setInt(1,id.intValue());
            int afected = statement.executeUpdate();
            if (afected > 0)
                return ent;
        }
        return null;
    }


    public Long getLastID() throws SQLException {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("select id from " + tableName + " order by id asc")) {
            ResultSet data = statement.executeQuery();
            Long i = 1l;
            while (data.next()) {
                if (data.getLong("id") != i)
                    return i;
                i++;
            }
            return i;
        }
    }

    protected abstract T parse(ResultSet data) throws SQLException;

}
