package JsonPCracker_1yyg;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FrameLoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadListener;
import com.teamdev.jxbrowser.chromium.events.ProvisionalLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;

public class Web_LoadListener implements LoadListener {
	
	@Override
	public void onDocumentLoadedInFrame(FrameLoadEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDocumentLoadedInMainFrame(LoadEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFailLoadingFrame(FailLoadingEvent arg0) {
		// TODO Auto-generated method stub
	}
	public boolean LoggingData(FinishLoadingEvent arg0)
	{
		DOMDocument dom = arg0.getBrowser().getDocument();
		DOMElement el =  dom.findElement(By.id("ul_menu"));
		List<DOMElement> lis = el.findElements(By.tagName("li"));
		for(DOMElement li : lis) 
		{
			if(li.getInnerHTML().contains("所有参与记录"))
			{
				li.click();		
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DOMDocument domNew = arg0.getBrowser().getDocument();
				
				List<DOMElement> ulsNew =  domNew.findElements(By.tagName("ul"));
				for (DOMElement ult : ulsNew)
				{
					if(ult.getInnerHTML().contains("查看云购码") == false)
					{
						continue;
					}
					List<DOMElement> lisNew = ult.findElements(By.tagName("li"));
					String time = "";
					String name = "";
					String people = "";
					String ip = "";
					for(DOMElement liss :lisNew)
					{		            		
						DOMElement timeEl = liss.findElement(By.className("time"));
						DOMElement nameEl = liss.findElement(By.className("name"));
						DOMElement peopleEl = liss.findElement(By.className("people"));						
						DOMElement ipEl = liss.findElement(By.className("ip"));
						DOMElement showCodeEl =  liss.findElement(By.name("showCode"));
						time = timeEl.getInnerText();
						name = nameEl.getInnerText();
						people = peopleEl.getInnerText();
						ip = ipEl.getInnerText();	
						if(time.contains("时间"))
						{
							continue;
						}
						if(showCodeEl == null)
						{
							continue;
						}
						else
						{
							showCodeEl.click();
						}
						try {
							Thread.sleep(200);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						DOMDocument domNewNew = arg0.getBrowser().getDocument();
						DOMElement code_ulEl = domNewNew.findElement(By.className("code-ul"));
						List<DOMElement> code_lis = code_ulEl.findElements(By.tagName("li"));
						try {
							FileWriter fw = new FileWriter("E:\\WorkSpace\\JsonPCracker_1yyg_ForJava\\log.txt",true);
							fw.write(time+","+name+","+people+","+ip);
							for(DOMElement code_lisItem : code_lis)
							{
								fw.write(",["+code_lisItem.getInnerText()+"]");
							}
							fw.write("\n");
							fw.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}		            	
				}
			}
		}
		return true;
	}
	@Override
	public void onFinishLoadingFrame(FinishLoadingEvent arg0)
	{
		LoggingData(arg0);
	}

	@Override
	public void onProvisionalLoadingFrame(ProvisionalLoadingEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartLoadingFrame(StartLoadingEvent arg0) {
		// TODO Auto-generated method stub
	}

}
