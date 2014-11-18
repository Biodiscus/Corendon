package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Role;

/**
 * © 2014, Biodiscus.net robin
 */
public class RoleModel {
    private static final RoleModel _default = new RoleModel();

    private RoleModel() {}

    public Role getRole(int id) {
        return new Role();
    }

    public static RoleModel getDefault() {
        return _default;
    }
}
