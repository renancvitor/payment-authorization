package model.services;

import model.entities.UserType;
import model.entities.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissaoService {

    private final Map<UserType, List<String>> permissoesPorTipoUsuario;

    public PermissaoService() {
        permissoesPorTipoUsuario = new HashMap<>();
        permissoesPorTipoUsuario.put(UserType.ADMIN, List.of("aprovarReprovarSolicitacoes",
                "visualizarTodasSolicitacoes", "gerenciarUsuarios", "realizarSolicitacoes"));
        permissoesPorTipoUsuario.put(UserType.GESTOR, List.of("aprovarReprovarSolicitacoes",
                "visualizarTodasSolicitacoes", "realizarSolicitacoes"));
        permissoesPorTipoUsuario.put(UserType.VISUALIZADOR, List.of("visualizarTodasSolicitacoes",
                "realizarSolicitacoes"));
        permissoesPorTipoUsuario.put(UserType.COMUM, List.of("realizarSolicitacoes"));
    }

    public List<String> getPermissoesByUserType(UserType userType) {
        return permissoesPorTipoUsuario.getOrDefault(userType, new ArrayList<>());
    }

    private boolean hasPermissao(Usuario usuario, String permissao) {
        return permissoesPorTipoUsuario.getOrDefault(usuario.getUserType(), List.of()).contains(permissao);
    }

    public boolean aprovarReprovarSolicitacoes(Usuario usuario) {
        return hasPermissao(usuario, "aprovarReprovarSolicitacoes");
    }

    public boolean visualizarTodasSolicitacoes(Usuario usuario) {
        return hasPermissao(usuario, "visualizarTodasSolicitacoes");
    }

    public boolean gerenciarUsuarios(Usuario usuario) {
        return hasPermissao(usuario, "gerenciarUsuarios");
    }

    public boolean realizarSolicitacoes(Usuario usuario) {
        return hasPermissao(usuario, "realizarSolicitacoes");
    }

    public boolean podeVerOutrasSolicitacoes(Usuario usuario) {
        return usuario.getUserType() != UserType.COMUM;
    }
}