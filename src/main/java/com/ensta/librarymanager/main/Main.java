package com.ensta.librarymanager.main;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

public class Main {

	public static void main(String[] args) {
		testLivre();
		testMembre();
		testEmprunt();
	}
	public static void testLivre() {
		LivreService livreService = LivreService.getInstance();
		try {
			System.out.println("Get by id: ");
			Livre alivre = livreService.getById(5);
			System.out.println(alivre);
			System.out.println("-----------------------------------------------------------------");
			//insert
			System.out.println("Insert et Delete: ");
			livreService.create("Le Petit Prince", "Antoine de Saint-Exupéry", "9782070408504");
//			livreService.delete(2);
			List<Livre> livres = livreService.getList();
			for(Livre livre: livres) {
				System.out.println(livre);
			}
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Get current book list: ");
			livres = livreService.getListDispo();
			for(Livre livre: livres) {
				System.out.println(livre);
			}
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Total: ");
			System.out.println(livreService.count());
		}catch(ServiceException e){
			e.printStackTrace();
		}
	}
	
	public static void testMembre() {
		MembreService membreService = MembreService.getInstance();
		try {
			System.out.println("Get by id: ");
			Membre membre = membreService.getById(5);
			System.out.println(membre);
			System.out.println("-----------------------------------------------------------------");
			//insert
			System.out.println("Insert et Delete: ");
//			Membre ziqi = new Membre(21,"MA","Ziqi","16 rue emile baudot, 91120",
//					"mmmmmmaziqi@gmail.com","0631261934", Abonnement.VIP);
			membreService.create("MA","Ziqi","16 rue emile baudot, 91120",
					"mmmmmmaziqi@gmail.com","0631261934");
//			membreService.delete(2);
			List<Membre> membres = membreService.getList();
			for(Membre amem: membres) {
				System.out.println(amem);
			}
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Get dispo membre list ");
			membres = membreService.getListMembreEmpruntPossible();
			for(Membre amem: membres) {
				System.out.println(amem);
			}
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Total: ");
			System.out.println(membreService.count());
		}catch(ServiceException e){
			e.printStackTrace();
		}
	}

	public static void testEmprunt() {
		try {

			EmpruntService empruntService = EmpruntService.getInstance();
			List<Emprunt> listeEmprunt = empruntService.getList();

			System.out.println("get Emprunt current list by Membre: ");
			listeEmprunt = empruntService.getListCurrentByMembre(5);
			for(Emprunt emprunt:listeEmprunt) {
				System.out.println(emprunt);
			}
			
			System.out.println("-----------------------------------------------------------------");
			System.out.println("get Emprunt current list by Livre: ");
			listeEmprunt = empruntService.getListCurrentByLivre(4);
			for(Emprunt emprunt:listeEmprunt) {
				System.out.println(emprunt);
			}
			
			System.out.println("-----------------------------------------------------------------");
			System.out.println("get Emprunt by id: ");
			System.out.println(empruntService.getById(2));
			
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Emprunt livre: ");
			empruntService.create(5, 1, LocalDate.now());
			listeEmprunt = empruntService.getListCurrent();
			for(Emprunt emprunt:listeEmprunt) {
				System.out.println(emprunt);
			}
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Retour livre: ");
			empruntService.returnBook(3);
			listeEmprunt = empruntService.getList();
			for(Emprunt emprunt:listeEmprunt) {
				System.out.println(emprunt);
			}
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Livre disponible: ");
			System.out.println(empruntService.isLivreDispo(3));
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Membre disponible: ");
			MembreService membreService = MembreService.getInstance();
			Membre aClient = membreService.getById(3);
			System.out.println(empruntService.isEmpruntPossible(aClient));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
