package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.ui.CLIAsker;
import com.excilys.cdb.ui.CLIView;
import com.excilys.cdb.ui.MenuOption;

public class CLIController {

	private CLIView view;
	private CLIAsker asker;

	public CLIController() {
		this.view = new CLIView();
		this.asker = new CLIAsker();
	}

	public CLIView getView() {
		return view;
	}

	public CLIAsker getAsker() {
		return asker;
	}

	public void executeChoice(int choiceId) throws SQLException {
		MenuOption choice = MenuOption.values()[choiceId - 1];
		switch (choice) {
		case LIST_COMPUTERS:
			executeListComputers();
			break;

		case LIST_COMPANIES:
			executeListCompanies();
			break;

		case SHOW_DETAILS:
			executeShowDetails();
			break;

		case CREATE_COMPUTER:
			executeInsertComputer();
			break;

		case UPDATE_COMPUTER:
			executeUpdateComputer();
			break;

		case DELETE_COMPUTER:
			executeDeleteComputer();
			break;

		default:
			break;
		}
	}
	private void executeListComputers() throws SQLException {
		ComputerDAO dao = ComputerDAO.getInstance();
		int computersCount = dao.CountComputers();
		int pagesCount = computersCount / Page.MAX_ITEMS;
		if (computersCount % Page.MAX_ITEMS != 0) {
			pagesCount++;
		}
		int pageNum = 1;
		while (pageNum <= pagesCount) {
			Page<Computer> p = new Page<Computer>(pageNum, pagesCount);
			p.setItems(dao.getSomeComputers(Page.MAX_ITEMS, (pageNum-1) * Page.MAX_ITEMS));
			view.printPage(p);
			String choice = asker.askPage(pageNum, pagesCount);
			if ("q".equalsIgnoreCase(choice)) {
				break;
			} else if ("a".equalsIgnoreCase(choice)) {
				pageNum = ((pageNum + pagesCount - 2) % pagesCount) + 1;
			} else {
				pageNum = (pageNum % pagesCount) + 1;
			}
		}
	}

	private void executeListCompanies() throws SQLException {
		CompanyDAO dao = CompanyDAO.getInstance();
		int companiesCount = dao.CountCompanies();
		int pagesCount = companiesCount / Page.MAX_ITEMS;
		if (companiesCount % Page.MAX_ITEMS != 0)
			pagesCount++;
		int pageNum = 1;
		while (pageNum <= pagesCount) {
			if (pageNum == 0)
				pageNum = pagesCount;
			Page<Company> p = new Page<Company>(pageNum, pagesCount);
			p.setItems(dao.getSomeCompanies(Page.MAX_ITEMS, (pageNum-1) * Page.MAX_ITEMS));
			view.printPage(p);
			String choice = asker.askPage(pageNum, pagesCount);
			if ("q".equals(choice))
				break;
			else if ("a".equals(choice)) {
				pageNum = ((pageNum + pagesCount - 2) % pagesCount) + 1;
			} else {
				pageNum = (pageNum % pagesCount) + 1;
			}
		}
		
	}

	private void executeShowDetails() {
		
	}
	
	private void executeInsertComputer() throws SQLException {
		String name = asker.askComputerName();
		LocalDate addDate = asker.askAddDate();
		LocalDate removeDate = null;
		while (true) {
			removeDate = asker.askRemoveDate();
			if (removeDate != null && addDate != null && removeDate.isBefore(addDate)) {
				System.out.println("La date de retrait ne peut être antérieure à la date d'ajout de l'ordinateur");
			} else {
				break;
			}
		}
		Company company = asker.askCompany();
		int id = ComputerDAO.getInstance().insertComputer(new Computer(0, name, addDate, removeDate, company));
		System.out.println("L'ordinateur a été créé avec l'ID " + id);
	}

	private void executeUpdateComputer() throws SQLException {
		int id = asker.askComputerId();
		String name = asker.askComputerName();
		LocalDate addDate = asker.askAddDate();
		LocalDate removeDate = null;
		while (true) {
			removeDate = asker.askRemoveDate();
			if (removeDate != null && addDate != null && removeDate.isBefore(addDate)) {
				System.out.println("La date de retrait ne peut être antérieure à la date d'ajout de l'ordinateur");
			} else {
				break;
			}
		}
		Company company = asker.askCompany();
		int updateCount = ComputerDAO.getInstance().updateComputer(new Computer(id, name, addDate, removeDate, company));
		System.out.println(updateCount + " ligne(s) a/ont été mise(s) à jour");
		
	}

	private void executeDeleteComputer() throws SQLException {
		int id = asker.askComputerId();
		int deleteCount = ComputerDAO.getInstance().deleteComputer(id);
		System.out.println(deleteCount + " ligne(s) a/ont été supprimée(s)");
	}

}
