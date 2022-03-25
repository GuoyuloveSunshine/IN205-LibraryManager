package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDao implements IMembreDao{
	private static MembreDao instance;
	
	private MembreDao() {};
	public static MembreDao getInstance() {
		if(instance == null) {
			instance = new MembreDao();
		}
		return instance;
	}
	
	@Override
	public List<Membre> getList() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT id, nom, prenom, adresse, email, telephone, abonnement "
					+ "FROM membre ORDER BY nom, prenom;");
			ResultSet rs = pstmt.executeQuery();
			List<Membre> listeMembre = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				Membre membre = this.getById(id);
				listeMembre.add(membre);
			}
			return listeMembre;

		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Membre getById(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String adresse = rs.getString("adresse");
			String email = rs.getString("email");
			String telephone = rs.getString("telephone");
			Abonnement abonnement =  Abonnement.valueof(rs.getString("abonnement"));
			
			Membre membre = new Membre(id,nom,prenom,adresse,email,telephone,abonnement);
			return membre;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO membre(nom, prenom, adresse, email, telephone,abonnement) VALUES (?, ?, ?, ?, ?, ?);");
			Abonnement abonn = Abonnement.BASIC;
			pstmt.setString(1, nom);
			pstmt.setString(2, prenom);
			pstmt.setString(3, adresse);
			pstmt.setString(4, email);
			pstmt.setString(5, telephone);
			pstmt.setString(6, abonn.toString());
		
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void update(Membre membre) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?");
			pstmt.setString(1, membre.getNom());
			pstmt.setString(2, membre.getPrenom());
			pstmt.setString(3, membre.getAdresse());
			pstmt.setString(4, membre.getEmail());
			pstmt.setString(5, membre.getTelephone());
			pstmt.setString(6, membre.getAbonnement().toString());
			pstmt.setInt(7, membre.getId());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		
	}

	@Override
	public void delete(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"DELETE FROM membre WHERE id = ?");
			pstmt.setInt(1, id);
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
					"SELECT COUNT(id) AS count FROM membre;");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

}
