package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
		public static void main(String[] args) {
				SellerDao sellerDao = DaoFactory.createSellerDao();

				Seller seller = sellerDao.findById(3);

				System.out.println(seller);

				System.out.println("=======================");

				List<Seller> sellerList = sellerDao.findByDepartment(new Department(2, null));

				for (Seller sellerFromList : sellerList) {
						System.out.println(sellerFromList);
				}

				System.out.println("======================");

				List<Seller> allSellers = sellerDao.findAll();

				for (Seller sellerFromAll : allSellers) {
						System.out.println(sellerFromAll);
				}

				System.out.println("=======================");
				Seller newSeller = new Seller(null, "Noah", "noah@icloud.com", new Date(), 3000.0, new Department(1, null));

				sellerDao.insert(newSeller);

				System.out.println(newSeller);
		}
}