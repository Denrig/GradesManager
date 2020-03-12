package Repository;

import Exceptions.IllegalArgumentException;
import Exceptions.ValidationException;
import Roles.User;
import Validator.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserRepository extends DBRepository<User> {
    public UserRepository(Validator validator, Properties props) {
        super(validator,props);
        this.tableName="users";
    }

    @Override
    protected User parse(ResultSet data) throws SQLException {
        return new User(data.getLong("id"),data.getString("role"),data.getString("fullname"),data.getString("name"),data.getString("pass"),data.getString("email"));
    }



    public User login(String user,String pass){
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("select * from users where name=? and pass=?")) {
            statement.setString(1,user);
            statement.setString(2,Integer.toString(pass.hashCode()));
            ResultSet data = statement.executeQuery();
            data.next();
            return parse(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }

    @Override
    public User save(User entity) throws ValidationException, IllegalArgumentException, SQLException {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("insert into users (id, name, pass, role, email, fullname) VALUES (?,?,?,?,?,?)")) {
            statement.setInt(1,entity.getId().intValue());
            statement.setString(2,entity.getUser());
            statement.setString(3,Integer.toString(entity.getPass().hashCode()));
            statement.setString(4,entity.getRoles().toString());
            statement.setString(5, entity.getEmail());
            statement.setString(6,entity.getNume());
            int afected=statement.executeUpdate();
            if(afected>0)
                return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User update(User entity) throws IllegalArgumentException, ValidationException, SQLException {
        try (PreparedStatement statement = jbdcUtils.getConnection().prepareStatement("update " + tableName + " set  name=?,pass=?,role=?,email=?,fullname=? where id=?")) {
            statement.setInt(6,entity.getId().intValue());
            statement.setString(1,entity.getUser());
            statement.setString(2,Integer.toString(entity.getPass().hashCode()));
            statement.setString(3,entity.getRoles().toString());
            statement.setString(4, entity.getEmail());
            statement.setString(5,entity.getNume());
            int afected=statement.executeUpdate();
            if(afected>0)
                return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    }

