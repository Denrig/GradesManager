package Roles;


import Entity.Entity;

import java.util.Objects;

public class User extends Entity<Long> {
    protected Roles roles;
    private String user;
    private String pass;
    private String nume;
    private String email;

    public Roles getRoles() {
        return roles;
    }

    public User(Long id){
        setId(id);
    }

    public User(Long id,String roles, String user, String pass, String nume, String email) {
        setId(id);
        this.roles = Roles.valueOf(roles);
        this.user = user;
        this.pass = pass;
        this.nume = nume;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "roles=" + roles +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                ", nume='" + nume + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user1 = (User) o;
        return roles == user1.roles &&
                Objects.equals(user, user1.user) &&
                Objects.equals(pass, user1.pass) &&
                Objects.equals(nume, user1.nume) &&
                Objects.equals(email, user1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roles, user, pass, nume, email);
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
