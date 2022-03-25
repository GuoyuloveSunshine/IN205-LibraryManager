package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDao implements ILivreDao{
	private static LivreDao instance;
	
	private LivreDao() {};
	public static LivreDao getInstance() {
		if(instance == null) {
			instance = new LivreDao();
		}
		return instance;
	}
	
	@Override
	public List<Livre> getList() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT id, titre, auteur, isbn FROM Livre");
			ResultSet rs = pstmt.executeQuery();
			List<Livre> listeLivre = new ArrayList<>();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				Livre livre = this.getById(id);
				listeLivre.add(livre);
			}
			return listeLivre;

		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Livre getById(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT titre, auteur, isbn FROM Livre WHERE id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String titre = rs.getString("titre");
			String auteur = rs.getString("auteur");
			String ISBN = rs.getString("ISBN");
			
			Livre livre = new Livre(id,titre,auteur,ISBN);
			return livre;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);");
			pstmt.setString(1, titre);
			pstmt.setString(2, auteur);
			pstmt.setString(3, isbn);
		
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	@Override
	public void update(Livre livre) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;");
			pstmt.setString(1, livre.getTitre());
			pstmt.setString(2, livre.getAuteur());
			pstmt.setString(3, livre.getIsbn());
			pstmt.setInt(4, livre.getId());
			
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
					"DELETE FROM livre WHERE id = ?");
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
					"SELECT COUNT(id) AS count FROM livre;");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

}
