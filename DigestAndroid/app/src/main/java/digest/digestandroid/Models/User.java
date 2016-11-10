package digest.digestandroid.Models;

import com.google.gson.Gson;

import java.util.Arrays;

/**
 * Created by Suyunu on 10.11.2016.
 */

public class User {
    // {"id":32,"username":"android","password":"1234","email":"asdf@asdf.com",
    // "first_name":"android","last_name":"android","status":1,
    // "role":{"id":2,"name":"user"},"session":"1c01360fd1a2aa97"}
    private int id;
    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private int status;
    private Role role;
    private String session;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", status=" + status +
                ", role=" + role.toString() +
                ", session='" + session + '\'' +
                '}';
    }

    private class Role {
        private int id;
        private String name;

        public Role() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Role{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}


