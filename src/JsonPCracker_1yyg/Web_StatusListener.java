package JsonPCracker_1yyg;

import com.teamdev.jxbrowser.chromium.events.StatusEvent;
import com.teamdev.jxbrowser.chromium.events.StatusListener;

public class Web_StatusListener implements StatusListener {

	public Web_StatusListener(int inBROWSER_ID,int inBROWSER_COUNT) {
		super();
		// TODO Auto-generated constructor stub
		BROWSER_ID = inBROWSER_ID;
		BROWSER_COUNT = inBROWSER_COUNT;
	}

	boolean OverGrep;
	public int BROWSER_ID;
	public int BROWSER_COUNT;
	
	@Override
	public void onStatusChange(StatusEvent arg0) {
		// TODO Auto-generated method stub
	}

}
