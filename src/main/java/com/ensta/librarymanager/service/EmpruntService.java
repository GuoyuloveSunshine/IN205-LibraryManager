package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;

public class EmpruntService implements IEmpruntService {
	private EmpruntDao empruntDao = EmpruntDao.getInstance();
	
	private static EmpruntService instance;
	private EmpruntService() {};
	public static EmpruntService getInstance() {
		if(instance == null) {
			instance = new EmpruntService();
		}
		return instance;
	}
	
	@Override
	public List<Emprunt> getList() throws ServiceException {
		try {
			return this.empruntDao.getList();
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws ServiceException {
		try {
			return this.empruntDao.getListCurrent();
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		try {
			return this.empruntDao.getListCurrentByMembre(idMembre);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		try {
			return this.empruntDao.getListCurrentByLivre(idLivre);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Emprunt getById(int id) throws ServiceException {
		try {
			return this.empruntDao.getById(id);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
		try {
			this.empruntDao.create(idMembre, idLivre, dateEmprunt);;
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		
	}

	@Override
	public void returnBook(int id) throws ServiceException {
		try {
			Emprunt emprunt = empruntDao.getById(id);
			LocalDate dateRetour = LocalDate.now();
			emprunt.setDateRetour(dateRetour);
			empruntDao.update(emprunt);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		
	}

	@Override
	public int count() throws ServiceException {
		try {
			return this.empruntDao.count();
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		try {
			List<Emprunt> emprunt = this.empruntDao.getListCurrentByLivre(idLivre);
			return(emprunt.isEmpty());
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		try {
			List<Emprunt> empruntByMem = this.empruntDao.getListCurrentByMembre(membre.getId());
			switch(membre.getAbonnement()) {
				case BASIC:
					return(empruntByMem.size() < 2);
				case PREMIUM:
					return(empruntByMem.size() < 5);
				case VIP:
					return(empruntByMem.size() < 20);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return false;
	}

}
