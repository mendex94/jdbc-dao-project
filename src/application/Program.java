package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

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
		}
}