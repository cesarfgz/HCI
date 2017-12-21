import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
 
def Message log01(Message message) {processData("XML_request", message);}
def Message log02(Message message) {processData("XML_response", message);}
def Message log03(Message message) {processData("log03", message);}
def Message log04(Message message) {processData("log04", message);}
def Message log05(Message message) {processData("log05", message);}
def Message log06(Message message) {processData("log06", message);}
def Message log07(Message message) {processData("log07", message);}
def Message log08(Message message) {processData("log08", message);}
def Message log09(Message message) {processData("log09", message);}
def Message log10(Message message) {processData("log10", message);}
 
def Message processData(String prefix, Message message) {
	def headers = message.getHeaders();
	def body = message.getBody(java.lang.String) as String;
	def messageLog = messageLogFactory.getMessageLog(message);
	
	for (header in headers) {
	   messageLog.setStringProperty("header." + header.getKey().toString(), header.getValue().toString())
	}
	for (property in properties) {
	   messageLog.setStringProperty("property." + property.getKey().toString(), property.getValue().toString())
	}
    if(messageLog != null){
        messageLog.addAttachmentAsString(prefix, body, "text/plain");
     }
    return message;
}