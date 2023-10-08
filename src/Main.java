import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Customer {
    private String registrationCode;
    private String fullName;
    private String email;
    private String telephone;

    public Customer(String registrationCode, String fullName, String email, String telephone) {
        this.registrationCode = registrationCode;
        this.fullName = fullName;
        this.email = email;
        this.telephone = telephone;
    }

    public Customer() {

    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

class Product {
    private String name;
    private String skuCode;
    private double unitPrice;

    public Product(String name, String skuCode, double unitPrice) {
        this.name = name;
        this.skuCode = skuCode;
        this.unitPrice = unitPrice;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}

class OrderLine {
    private Product product;
    private int quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderLine(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}

class Order {
    private List<OrderLine> orderLines;
    private Customer customer;
    private Date submissionDate;

    public Order(List<OrderLine> orderLines, Customer customer, Date submissionDate) {
        this.orderLines = orderLines;
        this.customer = customer;
        this.submissionDate = submissionDate;
    }

    public Order() {

    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
}

class DatabaseUtil {
    public static Connection getConnection() throws SQLException {
        // Implement your database connection logic here
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/customerorder", "root", "");
    }
}

class CustomerDAO {
    private final Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public void createCustomer(Customer customer) {
        String insertSQL = "INSERT INTO customers (registrationCode, fullName, email, telephone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, customer.getRegistrationCode());
            preparedStatement.setString(2, customer.getFullName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getTelephone());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM customers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setRegistrationCode(resultSet.getString("registrationCode"));
                customer.setFullName(resultSet.getString("fullName"));
                customer.setEmail(resultSet.getString("email"));
                customer.setTelephone(resultSet.getString("telephone"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
        return customers;
    }

    public Customer getCustomerById(String registrationCode) {
        String selectByIdSQL = "SELECT * FROM customers WHERE registrationCode= ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {
            preparedStatement.setString(1, registrationCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setRegistrationCode(resultSet.getString("registrationCode"));
                customer.setFullName(resultSet.getString("full_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setTelephone(resultSet.getString("telephone"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
        return null; // Customer not found
    }

    public void updateCustomer(Customer customer) {
        String updateSQL = "UPDATE customers SET full_name = ?, email = ?, telephone = ? WHERE registration_code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, customer.getFullName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getTelephone());
            preparedStatement.setString(4, customer.getRegistrationCode());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }

    public void deleteCustomer(String registrationCode) {
        String deleteSQL = "DELETE FROM customers WHERE registration_code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, registrationCode);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }
}

class ProductDAO {
    private final Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void createProduct(Product product) {
        String insertSQL = "INSERT INTO products (sku_code, name, unit_price) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, product.getSkuCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getUnitPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM products";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setSkuCode(resultSet.getString("sku_code"));
                product.setName(resultSet.getString("name"));
                product.setUnitPrice(resultSet.getDouble("unit_price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
        return products;
    }

    public Product getProductBySkuCode(String skuCode) {
        String selectBySkuCodeSQL = "SELECT * FROM products WHERE sku_code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectBySkuCodeSQL)) {
            preparedStatement.setString(1, skuCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setSkuCode(resultSet.getString("sku_code"));
                product.setName(resultSet.getString("name"));
                product.setUnitPrice(resultSet.getDouble("unit_price"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
        return null; // Product not found
    }

    public void updateProduct(Product product) {
        String updateSQL = "UPDATE products SET name = ?, unit_price = ? WHERE sku_code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getUnitPrice());
            preparedStatement.setString(3, product.getSkuCode());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }

    public void deleteProduct(String skuCode) {
        String deleteSQL = "DELETE FROM products WHERE sku_code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, skuCode);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }
}

class OrderDAO {
    private final Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public void createOrder(Order order) {
        String insertSQL = "INSERT INTO orders (customer_registration_code, submission_date) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, order.getCustomer().getRegistrationCode());
            preparedStatement.setDate(2, order.getSubmissionDate());

            preparedStatement.executeUpdate();

            // Retrieve the auto-generated order ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                // Handle the generated order ID as needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM orders";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {

            while (resultSet.next()) {
                Order order = new Order();
                // Populate order fields as needed
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        String selectByIdSQL = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {
            preparedStatement.setInt(1, orderId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Order order = new Order();
                // Populate order fields as needed
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
        return null; // Order not found
    }

    public void updateOrder(Order order) {
        String updateSQL = "UPDATE orders SET submission_date = ? WHERE customer_registration_code = ? AND submission_date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setDate(1, order.getSubmissionDate());
            preparedStatement.setString(2, order.getCustomer().getRegistrationCode());
            preparedStatement.setDate(3, order.getSubmissionDate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }

    public void deleteOrder(int orderId) {
        String deleteSQL = "DELETE FROM orders WHERE order_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, orderId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }
}

class Main {
    public static void main(String[] args) {
        try {
            // Initialize database connection
            Connection connection = DatabaseUtil.getConnection();

            // Create DAO instances
            CustomerDAO customerDAO = new CustomerDAO(connection);
            ProductDAO productDAO = new ProductDAO(connection);
            OrderDAO orderDAO = new OrderDAO(connection);

            // Initialize customer data with a registration code
            Customer newCustomer = new Customer();
            newCustomer.setRegistrationCode("ABC123"); // Set a valid registration code
            newCustomer.setFullName("John Doe");
            newCustomer.setEmail("john@example.com");
            newCustomer.setTelephone("123-456-7890");

            customerDAO.createCustomer(newCustomer);

            List<Customer> customers = customerDAO.getAllCustomers();
            // Handle retrieved customers as needed

            Customer customerById = customerDAO.getCustomerById("customer_id");
            // Handle retrieved customer as needed

            // Similar operations for products and orders

            // Close the database connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }
}