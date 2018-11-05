package listener;


import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

import util.PropertyFileRead;

public class TestListener  extends TestListenerAdapter {

	PropertyFileRead PropertyFileRead = new PropertyFileRead();
	
	public TestListener(){
	super();	
	}
	
	@Override
	public void onStart(ITestContext testContext) {
		System.out.println("Execution Starts");
	}

	@Override
	public void onFinish(ITestContext testContext) {
		System.out.println("Execution Completed");
	}
}
