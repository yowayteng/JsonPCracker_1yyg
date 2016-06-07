package JsonPCracker_1yyg;

import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.events.ScriptContextListener;

public class Web_ScriptContextListener implements ScriptContextListener {

	public Web_ScriptContextListener(int inBROWSER_ID,int inBROWSER_COUNT) {
		super();
		// TODO Auto-generated constructor stub
		BROWSER_ID = inBROWSER_ID;
		BROWSER_COUNT = inBROWSER_COUNT;
	}

	boolean OverGrep;
	public int BROWSER_ID;
	public int BROWSER_COUNT;
	
	@Override
	public void onScriptContextCreated(ScriptContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onScriptContextDestroyed(ScriptContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
