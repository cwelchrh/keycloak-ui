import { lazy } from "react";
import type { Path } from "react-router-dom-v5-compat";
import { generatePath } from "react-router-dom-v5-compat";
import type { RouteDef } from "../../route-config";

type OidcType = "keycloak-oidc" | "oidc";

export type IdentityProviderOidcParams = { id: OidcType; realm: string };

export const IdentityProviderOidcRoute: RouteDef = {
  path: "/:realm/identity-providers/:id/add",
  component: lazy(() => import("../add/AddOpenIdConnect")),
  breadcrumb: (t) => t("identity-providers:addOpenIdProvider"),
  access: "manage-identity-providers",
};

export const toIdentityProviderOidc = (
  params: IdentityProviderOidcParams
): Partial<Path> => ({
  pathname: generatePath(IdentityProviderOidcRoute.path, params),
});
