package learn.resume.builder.data;

import learn.resume.builder.models.AppRole;

public interface AppRoleRepo {
    AppRole findByName(String roleName);
}
