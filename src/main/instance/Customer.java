package instance;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name = "Customer.findAll",query = "select c from Customer c")
@Table(name = "customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "customer_name")
    private String customerName;

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public Customer() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return customerName;
    }
}