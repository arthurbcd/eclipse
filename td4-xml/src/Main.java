import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Main {

    public static void main(String[] args) throws Exception {
        File file = new File("sushiexpress.xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        List<Sushi> menu = readMenu(doc);
        List<Order> orders = readOrders(doc, menu);

        for (Sushi sushi : menu) {
            System.out.println(sushi);
        }
        System.out.println();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private static List<Sushi> readMenu(Document doc) {
        List<Sushi> menu = new ArrayList<>();
        NodeList sushiList = doc.getElementsByTagName("sushi");
        for (int i = 0; i < sushiList.getLength(); i++) {
            Element sushiElement = (Element) sushiList.item(i);
            int id = Integer.parseInt(sushiElement.getAttribute("id"));
            String name = sushiElement.getElementsByTagName("name").item(0).getTextContent();
            int price = Integer.parseInt(sushiElement.getElementsByTagName("price").item(0).getTextContent());
            menu.add(new Sushi(id, name, price));
        }
        return menu;
    }

    private static List<Order> readOrders(Document doc, List<Sushi> menu) {
        Map<Integer, Sushi> menuById = new HashMap<>();
        for (Sushi sushi : menu) {
            menuById.put(sushi.getId(), sushi);
        }

        List<Order> orders = new ArrayList<>();
        NodeList orderList = doc.getElementsByTagName("order");
        for (int i = 0; i < orderList.getLength(); i++) {
            Element orderElement = (Element) orderList.item(i);
            int orderId = Integer.parseInt(orderElement.getAttribute("id"));
            String status = orderElement.getElementsByTagName("status").item(0).getTextContent();

            List<Sushi> items = new ArrayList<>();
            Element itemsElement = (Element) orderElement.getElementsByTagName("items").item(0);
            NodeList itemList = itemsElement.getElementsByTagName("item");
            for (int j = 0; j < itemList.getLength(); j++) {
                Element itemElement = (Element) itemList.item(j);
                int sushiId = Integer.parseInt(itemElement.getAttribute("id"));
                Sushi sushi = menuById.get(sushiId);
                if (sushi != null) {
                    items.add(sushi);
                }
            }

            orders.add(new Order(orderId, items, status));
        }
        return orders;
    }
}

