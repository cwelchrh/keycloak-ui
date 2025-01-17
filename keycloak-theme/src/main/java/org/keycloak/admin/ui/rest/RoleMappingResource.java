package org.keycloak.admin.ui.rest;

import static org.keycloak.admin.ui.rest.model.RoleMapper.convertToRepresentation;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.keycloak.admin.ui.rest.model.ClientRole;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;

public abstract class RoleMappingResource {
    private RealmModel realm;
    private AdminPermissionEvaluator auth;

    public final Stream<ClientRole> mapping(Predicate<RoleModel> predicate) {
        return realm.getClientsStream().flatMap(RoleContainerModel::getRolesStream).filter(predicate)
                .filter(auth.roles()::canMapClientScope).map(roleModel -> convertToRepresentation(roleModel, realm.getClientsStream()));
    }

    public final List<ClientRole> mapping(Predicate<RoleModel> predicate, long first, long max, final String search) {

        return mapping(predicate).filter(clientRole -> clientRole.getClient().contains(search) || clientRole.getRole().contains(search))

                .skip("".equals(search) ? first : 0).limit(max).collect(Collectors.toList());
    }

    public RoleMappingResource(RealmModel realm, AdminPermissionEvaluator auth) {
        this.realm = realm;
        this.auth = auth;
    }
}
