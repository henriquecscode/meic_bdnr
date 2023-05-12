package app.service;

import app.domain.User;
import app.domain.derivatives.UserInfo;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends GeneralService {

    public UserService(OrientGraphFactory factory) {
        super(factory);
    }

    public List<User> getUsers() {
        setGraph();

        List<User> users = new ArrayList<>();
        for (Vertex v : graph.getVerticesOfClass("User")) {
            User user = User.fromVertex(v);
            users.add(user);
        }

        return users;
    }
}
