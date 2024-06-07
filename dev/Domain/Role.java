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
    public void addCantDo(Role role) {
        // Cant add same role to my cant do list.
        if (role.equals(this)) return;
        this.cantDo.add(role);
    }

    // remove roles - cant do
    public void removeCantDo(Role role) {
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
//toString

    @Override
    public String toString() {
        return name;
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
