package taskManager.utility;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;
import taskManager.TaskManager_LIFT1;
import taskManager.TaskManagerDataSource;

public class CommunicationUtility {
	private ArbiAgent taskManager;
	private RequestManager rm;
	private TaskManagerDataSource ds;
	
	public CommunicationUtility(ArbiAgent agent,TaskManagerDataSource ds) {
		taskManager = agent;
		rm = new RequestManager();
		this.ds = ds;
		
	}

	public void assertToLTM(String data) {
		ds.assertFact(data);
	}
	
	public void inform(String receiver, String content) {
		System.out.println("inform : " + receiver + " " + content);

		taskManager.send(receiver, content);

	}	
	
	public void unsubscribe(String receiver, String content) {
		System.out.println("unsubscribe : " + receiver + " " + content);

		taskManager.unsubscribe(receiver, content);
	}

	public void repeatMove(String location) {
		System.out.println("Request");
		String result = "";
		int count = 2;

		while (result.equals("true") == false) {
			result = taskManager.request(TaskManager_LIFT1.ACTION_ADRESS, "(move " + location + " (actionID " + count + "))");
			count++;
			
			GeneralizedList gl;
			try {
				gl = GLFactory.newGLFromGLString(result);
				result = gl.getExpression(0).asGeneralizedList().getExpression(2).toString();
				System.out.println(result);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
		}
		
		System.out.println("result : " + result);

	}

	public String sendQuery(String receiver, String content) {
		System.out.println("query : " + receiver + " " + content);
		String result = "";

		result = taskManager.query(receiver, content);

		System.out.println("query result :" + result);
		return result;

	}

	public String request(String receiver, String content) {
		System.out.println("Request : " + receiver + " " + content);
		String result = "";

		result = taskManager.request(receiver, content);
		System.out.println("result : " + result);

		return result;
	}

	public void test() {
		System.out.println("testPrint");

	}

	public void setResponse(String response) {
		rm.setResponse(response);
	}
	
	public void subscribe(String receiver,String content){
		taskManager.subscribe(receiver, content);
	}
	
	public void updateFact(String content) {
		ds.updateFact(content);
	}
	
	public String toString(){
		return "CommunicationUtility";
	}
}
