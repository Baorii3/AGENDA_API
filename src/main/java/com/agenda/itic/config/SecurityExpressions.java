package com.agenda.itic.config;

public final class SecurityExpressions {

    private SecurityExpressions() {
    }

    public static final String IS_ADMIN = "@securityService.isAdmin()";

    public static final String ACTIVITAT_READ = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).ACTIVITAT, T(com.agenda.itic.model.Accio).READ)";
    public static final String ACTIVITAT_CREATE = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).ACTIVITAT, T(com.agenda.itic.model.Accio).CREATE)";
    public static final String ACTIVITAT_DELETE = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).ACTIVITAT, T(com.agenda.itic.model.Accio).DELETE)";

    public static final String SALA_READ = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).SALA, T(com.agenda.itic.model.Accio).READ)";
    public static final String SALA_CREATE = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).SALA, T(com.agenda.itic.model.Accio).CREATE)";
    public static final String SALA_UPDATE = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).SALA, T(com.agenda.itic.model.Accio).UPDATE)";
    public static final String SALA_DELETE = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).SALA, T(com.agenda.itic.model.Accio).DELETE)";

    public static final String USUARI_READ = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).USUARI, T(com.agenda.itic.model.Accio).READ)";
    public static final String USUARI_CREATE = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).USUARI, T(com.agenda.itic.model.Accio).CREATE)";
    public static final String USUARI_UPDATE = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).USUARI, T(com.agenda.itic.model.Accio).UPDATE)";
    public static final String USUARI_DELETE = "@securityService.hasPermission(T(com.agenda.itic.model.RecursNom).USUARI, T(com.agenda.itic.model.Accio).DELETE)";
}
