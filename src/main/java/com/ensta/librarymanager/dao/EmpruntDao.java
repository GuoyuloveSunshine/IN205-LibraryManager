package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.h2.util.StringUtils;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDao implements IEmpruntDao {
	private static EmpruntDao instance;
	private EmpruntDao() {};
	
	public static EmpruntDao getInstance() {
		if(instance == null) {
			instance = new EmpruntDao();
		}
		return instance;
	}
	@Override
	public List<Emprunt> getList() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, "
					+ "abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour \n"
					+ "FROM emprunt AS e \n"
					+ "INNER JOIN membre ON membre.id = e.idMembre \n"
					+ "INNER JOIN livre ON livre.id = e.idLivre \n"
					+ "ORDER BY dateRetour DESC;");
			ResultSet rs = pstmt.executeQuery();
			List<Emprunt> listeEmprunt = new ArrayList<>();
			while (rs.next()) {
				//obtenir emprunt
				int id = rs.getInt("id");
				String dateEmprunt = rs.getString("dateEmprunt");
				String dateRetour = rs.getString("dateRetour");
				//obtenir membre
				int idMembre = rs.getInt("idMembre");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				Abonnement abonnement =  Abonnement.valueof(rs.getString("abonnement"));
				Membre membre = new Membre(idMembre,nom,prenom,adresse,email,telephone,abonnement);
				//obtenir livre
				int idLivre = rs.getInt("idLivre");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");
				Livre livre = new Livre(idLivre,titre,auteur,ISBN);
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
				LocalDate dateEmpt = LocalDate.parse(dateEmprunt,formatter);
				LocalDate dateRetr;
				if(StringUtils.isNullOrEmpty(dateRetour)) {
					dateRetr = null;
				}
				else {
					dateRetr = LocalDate.parse(dateRetour,formatter);
				}
				listeEmprunt.add(new Emprunt(id,membre,livre,dateEmpt,dateRetr));
			}
			return listeEmprunt;

		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, "
					+ "abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour \n"
					+ "FROM emprunt AS e \n"
					+ "INNER JOIN membre ON membre.id = e.idMembre \n"
					+ "INNER JOIN livre ON livre.id = e.idLivre \n"
					+ "WHERE dateRetour IS NULL;");
			ResultSet rs = pstmt.executeQuery();
			List<Emprunt> listeEmprunt = new ArrayList<>();
			while (rs.next()) {
				//obtenir emprunt
				int id = rs.getInt("id");
				String dateEmprunt = rs.getString("dateEmprunt");
				String dateRetour = rs.getString("dateRetour");
				//obtenir membre
				int idMembre = rs.getInt("idMembre");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				Abonnement abonnement =  Abonnement.valueof(rs.getString("abonnement"));
				Membre membre = new Membre(idMembre,nom,prenom,adresse,email,telephone,abonnement);
				//obtenir livre
				int idLivre = rs.getInt("idLivre");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");
				Livre livre = new Livre(idLivre,titre,auteur,ISBN);
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
				LocalDate dateEmpt = LocalDate.parse(dateEmprunt,formatter);
				LocalDate dateRetr;
				if(StringUtils.isNullOrEmpty(dateRetour)) {
					dateRetr = null;
				}
				else {
					dateRetr = LocalDate.parse(dateRetour,formatter);
				}
				listeEmprunt.add(new Emprunt(id,membre,livre,dateEmpt,dateRetr));
			}
			return listeEmprunt;

		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, "
					+ "abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour \n"
					+ "FROM emprunt AS e \n"
					+ "INNER JOIN membre ON membre.id = e.idMembre \n"
					+ "INNER JOIN livre ON livre.id = e.idLivre \n"
					+ "WHERE dateRetour IS NULL AND membre.id = ?");
			pstmt.setInt(1, idMembre);
			ResultSet rs = pstmt.executeQuery();
			List<Emprunt> listeEmprunt = new ArrayList<>();
			while (rs.next()) {
				//obtenir emprunt
				int id = rs.getInt("id");
				String dateEmprunt = rs.getString("dateEmprunt");
				String dateRetour = rs.getString("dateRetour");
				//obtenir membre
//				int idMembre = rs.getInt("idMembre");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				Abonnement abonnement =  Abonnement.valueof(rs.getString("abonnement"));
				Membre membre = new Membre(idMembre,nom,prenom,adresse,email,telephone,abonnement);
				//obtenir livre
				int idLivre = rs.getInt("idLivre");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");
				Livre livre = new Livre(idLivre,titre,auteur,ISBN);
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
				LocalDate dateEmpt = LocalDate.parse(dateEmprunt,formatter);
				LocalDate dateRetr;
				if(StringUtils.isNullOrEmpty(dateRetour)) {
					dateRetr = null;
				}
				else {
					dateRetr = LocalDate.parse(dateRetour,formatter);
				}
				listeEmprunt.add(new Emprunt(id,membre,livre,dateEmpt,dateRetr));
			}
			return listeEmprunt;

		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, "
					+ "abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour \n"
					+ "FROM emprunt AS e \n"
					+ "INNER JOIN membre ON membre.id = e.idMembre \n"
					+ "INNER JOIN livre ON livre.id = e.idLivre \n"
					+ "WHERE dateRetour IS NULL AND livre.id = ?");
			pstmt.setInt(1, idLivre);
			ResultSet rs = pstmt.executeQuery();
			List<Emprunt> listeEmprunt = new ArrayList<>();
			while (rs.next()) {
				//obtenir emprunt
				int id = rs.getInt("id");
				String dateEmprunt = rs.getString("dateEmprunt");
				String dateRetour = rs.getString("dateRetour");
				//obtenir membre
				int idMembre = rs.getInt("idMembre");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				Abonnement abonnement =  Abonnement.valueof(rs.getString("abonnement"));
				Membre membre = new Membre(idMembre,nom,prenom,adresse,email,telephone,abonnement);
				//obtenir livre
//				int idLivre = rs.getInt("idLivre");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");
				Livre livre = new Livre(idLivre,titre,auteur,ISBN);
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
				LocalDate dateEmpt = LocalDate.parse(dateEmprunt,formatter);
				LocalDate dateRetr;
				if(StringUtils.isNullOrEmpty(dateRetour)) {
					dateRetr = null;
				}
				else {
					dateRetr = LocalDate.parse(dateRetour,formatter);
				}
				listeEmprunt.add(new Emprunt(id,membre,livre,dateEmpt,dateRetr));
			}
			return listeEmprunt;

		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Emprunt getById(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT e.id AS idMembre, nom, prenom, adresse, email, telephone, "
					+ "abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour \n"
					+ "FROM emprunt AS e \n"
					+ "INNER JOIN membre ON membre.id = e.idMembre \n"
					+ "INNER JOIN livre ON livre.id = e.idLivre \n"
					+ "WHERE e.id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			//obtenir emprunt
//			int id = rs.getInt("id");
			String dateEmprunt = rs.getString("dateEmprunt");
			String dateRetour = rs.getString("dateRetour");
			//obtenir membre
			int idMembre = rs.getInt("idMembre");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String adresse = rs.getString("adresse");
			String email = rs.getString("email");
			String telephone = rs.getString("telephone");
			Abonnement abonnement =  Abonnement.valueof(rs.getString("abonnement"));
			Membre membre = new Membre(idMembre,nom,prenom,adresse,email,telephone,abonnement);
			//obtenir livre
			int idLivre = rs.getInt("idLivre");
			String titre = rs.getString("titre");
			String auteur = rs.getString("auteur");
			String ISBN = rs.getString("isbn");
			Livre livre = new Livre(idLivre,titre,auteur,ISBN);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
			LocalDate dateEmpt = LocalDate.parse(dateEmprunt,formatter);
			LocalDate dateRetr;
			if(StringUtils.isNullOrEmpty(dateRetour)) {
				dateRetr = null;
			}
			else {
				dateRetr = LocalDate.parse(dateRetour,formatter);
			}
			return new Emprunt(id,membre,livre,dateEmpt,dateRetr);

		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour)\n"
					+ "VALUES (?, ?, ?, ?);");
			pstmt.setInt(1, idMembre);
			pstmt.setInt(2, idLivre);
			pstmt.setString(3, dateEmprunt.toString());
			pstmt.setString(4, null);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		
	}

	@Override
	public void update(Emprunt emprunt) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? \n"
					+ "WHERE id = ?");
			pstmt.setInt(1, emprunt.getMembre().getId());
			pstmt.setInt(2, emprunt.getLivre().getId());
			pstmt.setString(3, emprunt.getDateEmprunt().toString());
			pstmt.setString(4, emprunt.getDateRetour().toString());
			pstmt.setInt(5, emprunt.getId());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		
	}

	@Override
	public int count() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT COUNT(id) AS count FROM emprunt;");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

}
