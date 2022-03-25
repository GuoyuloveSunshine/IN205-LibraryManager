package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;

public class MembreService implements IMembreService{
	private MembreDao membreDao= MembreDao.getInstance();
	private static MembreService instance;
	
	private MembreService() {};
	public static MembreService getInstance() {
		if(instance == null) {
			instance = new MembreService();
		}
		return instance;
	}
	@Override
	public List<Membre> getList() throws ServiceException {
		try {
			return this.membreDao.getList();
		}catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		try {
			List<Membre> listeMembre = this.membreDao.getList();
			List<Membre> dispoMembre = new ArrayList<>();
			EmpruntService empruntService = EmpruntService.getInstance();
			for(Membre mem:listeMembre) {
				if(empruntService.isEmpruntPossible(mem)) {
					dispoMembre.add(mem);
				}
			}
			return dispoMembre;
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		try {
			return this.membreDao.getById(id);
		} catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
		try {
			if(nom == null || prenom == null) {
				throw new ServiceException();
			}
			return this.membreDao.create(nom, prenom, adresse, email, telephone);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void update(Membre membre) throws ServiceException {
		try {
			if(membre.getNom() == null || membre.getPrenom() == null) {
				throw new ServiceException();
			}
			this.membreDao.update(membre);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void delete(int id) throws ServiceException {
		try {
			this.membreDao.delete(id);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		
	}

	@Override
	public int count() throws ServiceException {
		try {
			return this.membreDao.count();
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
