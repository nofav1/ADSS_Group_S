package Domain;

import java.util.ArrayList;
import java.util.List;

public class Role {
    private String name;
    private List<Role> cantDo;

    public Role(String name) {
        this.name = name;
        this.cantDo = new ArrayList<>();
    }

    // Add to this role, other roles it cant perform
    public void addCantDo(Worker worker, Role role) {
        if (!worker.getRoles().contains(role))
            this.cantDo.add(role);
    }

    // remove roles - cant do
    public void removeCantDo(Worker worker, Role role) {
        if (worker.getRoles().contains(role))
            this.cantDo.remove(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getCantDo() {
        return cantDo;
    }

    public void setCantDo(List<Role> cantDo) {
        this.cantDo = cantDo;
    }

    // Equality by role NAME.
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Role otherRole) {
            return this.name.equals(otherRole.name);
        }
        // Otherwise..
        return false;
    }
}
