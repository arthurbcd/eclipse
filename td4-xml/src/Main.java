import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class Main {

	public static void main(String[] args) throws Exception {
		File file = new File("sushiexpress.xml");
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(file);
		doc.getDocumentElement().normalize();

		System.out.println("Menu de sushi:");
		NodeList sushiList = doc.getElementsByTagName("sushi");
		for (int i = 0; i < sushiList.getLength(); i++) {
			Element sushi = (Element) sushiList.item(i);
			System.out.println("- id=" + sushi.getAttribute("id") + ", name=" + sushi.getElementsByTagName("name").item(0).getTextContent() + ", price=" + sushi.getElementsByTagName("price").item(0).getTextContent());
		}

		System.out.println("\nPedidos:");
		NodeList orderList = doc.getElementsByTagName("order");
		for (int i = 0; i < orderList.getLength(); i++) {
			Element order = (Element) orderList.item(i);
			System.out.print("- order id=" + order.getAttribute("id") + ", items=");
			NodeList items = order.getElementsByTagName("item");
			for (int j = 0; j < items.getLength(); j++) {
				Element item = (Element) items.item(j);
				System.out.print(item.getAttribute("id") + (j + 1 < items.getLength() ? ", " : ""));
			}
			System.out.println(", status=" + order.getElementsByTagName("status").item(0).getTextContent());
		}
	}
}

