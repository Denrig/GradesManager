package Services;

import Repository.UserRepository;
import Roles.User;
import Validator.UserValidator;

public class LoginService extends EntityService<User> {
    private String prop;
    public LoginService() {
        this.repository=new UserRepository(new UserValidator());
    }

    public String getProp() {
        return prop;
    }

    public User login(String user, String pass){
       User user1= ((UserRepository) repository).login(user,pass);
       switch (user1.getRoles()){
           case Admin:
           {    prop="Resources/Student.properties";
               return user1;}
           default:
               return null ;
       }
    }
}
