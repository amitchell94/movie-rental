package old_package;

public class OldApplication {
    //        Scanner scanner = new Scanner(System.in);
//
//
//
//
//        // create object of class so we can call non-static insertDbEntry() method
//        Application dbConnecter = new Application();
//        DbPrinter dbPrinter = new DbPrinter();
//        DbModifier dbModifier = new DbModifier();
//        DbAccessor dbAccessor = new DbAccessor();
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(dbConnecter.createConnectionUrl());
//
//            System.out.println("Enter the customer name");
//            String customerName = scanner.next();
//
//            Integer customerID = dbAccessor.getCustomer(customerName,"c_id","c_name","customers",connection);
//
//            dbPrinter.printMovieList(connection);
//
//            System.out.println("Enter the movie title of your choice");
//            String movieTitle = scanner.next();
//
//            Integer movieID = dbAccessor.getCustomer(movieTitle,"m_id","m_title","movies",connection);
//
//            System.out.println("Enter the rental date in the YYYY-MM-DD format");
//            String rentalDate = scanner.next();
//            System.out.println("Enter the return date in the YYYY-MM-DD format");
//            String returnDate = scanner.next();
//            System.out.println("Enter the cost");
//            double cost = scanner.nextDouble();
//
//            dbModifier.insertDbEntry(customerID, movieID, rentalDate, returnDate, cost, connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
}
