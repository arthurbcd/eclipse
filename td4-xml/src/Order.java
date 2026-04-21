import java.util.List;

public class Order {

    private int id;
    private List<Sushi> items;
    private String status;

    public Order(int id, List<Sushi> items, String status) {
        this.id = id;
        this.items = items;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public List<Sushi> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", items=" + items + ", status='" + status + "'}";
    }
}
