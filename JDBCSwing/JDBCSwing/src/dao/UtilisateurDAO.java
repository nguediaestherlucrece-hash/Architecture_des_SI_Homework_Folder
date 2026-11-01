package dao;

import models.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface UtilisateurDAO {
    void create(Utilisateur user);
    Utilisateur findById(int id) throws SQLException;
    List<Utilisateur> findAll();
    void update(Utilisateur user);
    void delete(int id);
}
