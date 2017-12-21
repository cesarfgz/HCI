import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.xml.XmlUtil;



def Message processData(Message message) {
	def body = message.getBody(java.lang.String) as String;
	def messageLog = messageLogFactory.getMessageLog(message);

	def root = new XmlSlurper().parseText(body);
	def data = "YEAR;DEUDOR;BRUTO;NETO;BRUTO_CURR;NETO_CURR";

	root.table.row.each{ row ->
		def linea = "";
		row.cell.each{ cell ->
			if (linea==""){
				linea = linea + formatImporte(cell.text());
			} else {
				linea = linea+";"+formatImporte(cell.text());
			}
		}

		linea = linea + ";EUR;EUR"; //La moneda se pone EUR simpre
		data = data + "\n" + linea;


	}

	Node inputXml = new XmlParser().parseText(body);
	Node doc = inputXml.getByName("documentation").get(0);
	doc.setValue(data);
	message.setBody(XmlUtil.serialize(inputXml));


	return message;
}

def formatImporte(cell) {

	String out = cell;

	if (cell.contains("e")) {
		try {
			BigDecimal importe = new BigDecimal(cell);
			out = String.format("%.2f", importe.doubleValue());
		} catch (Throwable e) {}
	}

	return out;
}