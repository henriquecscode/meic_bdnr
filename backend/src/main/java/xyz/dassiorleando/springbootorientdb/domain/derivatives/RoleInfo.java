package xyz.dassiorleando.springbootorientdb.domain.derivatives;

import xyz.dassiorleando.springbootorientdb.domain.Role;
import xyz.dassiorleando.springbootorientdb.domain.Worker;

public class RoleInfo {

    Worker worker;
    Role role;

    public RoleInfo() {
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleInfo{" +
                "worker=" + worker +
                ", role=" + role +
                '}';
    }
}
