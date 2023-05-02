package xyz.dassiorleando.springbootorientdb.domain;

import com.tinkerpop.blueprints.Vertex;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class User extends Person {
    private String username;

    private String password;

    public User() {
    }

    public static User fromODocument(ODocument oDocument) {
        User user = new User();
        user.name = oDocument.field("name");
        user.username = oDocument.field("username");
        user.password = oDocument.field("password");

        return user;
    }

    public static User fromVertex(Vertex vertex) {
        User user = new User();
        user.name = vertex.getProperty("name");
        user.username = vertex.getProperty("username");
        user.password = vertex.getProperty("password");

        return user;
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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
