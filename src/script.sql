-- Create the 'Products' table
CREATE TABLE IF NOT EXISTS Products (
    skuCode VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unitPrice DECIMAL(10, 2) NOT NULL
);

-- Create the 'Orders' table
CREATE TABLE IF NOT EXISTS Orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_registrationCode VARCHAR(255) NOT NULL,
    submissionDate DATE NOT NULL,
    FOREIGN KEY (customer_registrationCode) REFERENCES Customers(registrationCode)
);

-- Create the 'OrderLines' table
CREATE TABLE IF NOT EXISTS OrderLines (
    orderLine_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    skuCode VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (skuCode) REFERENCES Products(skuCode)
);