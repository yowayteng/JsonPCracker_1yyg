package JsonPCracker_1yyg;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

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
	
	public Web_LoadListener(int inBROWSER_ID,int inBROWSER_COUNT) {
		super();
		// TODO Auto-generated constructor stub
		BROWSER_ID = inBROWSER_ID;
		BROWSER_COUNT = inBROWSER_COUNT;
	}

	boolean OverGrep;
	public int BROWSER_ID;
	public int BROWSER_COUNT;
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
	public boolean OpenWebAndClickShowList(FinishLoadingEvent arg0)
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
				return true;
			}
		}
		return false;
	}
	public boolean CheckDataGetOK(FinishLoadingEvent arg0)
	{
		DOMDocument domNew = arg0.getBrowser().getDocument();
		List<DOMElement> ulsNew =  domNew.findElements(By.tagName("ul"));
		for (DOMElement ult : ulsNew)
		{
			if(ult.getInnerHTML().contains("查看云购码") == true)
			{
				return true;
			}
		}
		return false;
	}
	public boolean LoggingData_toDB(
			String showCode,
			String hidGoodsID ,
			String hidCodeID ,
			String Title ,	
			String buyid ,
			String name ,
			String ip ,
			String time
			)
	{		
		String rtunVal;
		String url = "jdbc:ucanaccess://E:\\WorkSpace\\JAVA_1yyg.mdb" ;  
		Properties properties = new Properties() ;  
		properties.setProperty("charSet", "gbk") ;          //这里设置是为了防止查询的时候出现乱码  
		Connection conn = null;  
		try {
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = null;
		java.sql.Statement statement;
		String Sql = "";
		try {
			conn = DriverManager.getConnection(url);			
			statement = conn.createStatement();	
			{
			Sql += "SELECT Count(*) AS RTab ";
			Sql += "FROM MSysObjects ";
			Sql += "WHERE (((MSysObjects.Name) Like 'YunBuyTable_"+ hidGoodsID + hidCodeID +"'));";
			}
			rs = statement.executeQuery(Sql);  
			rs.next();
			rtunVal = new String(rs.getString(1).getBytes(),"UTF-8");
			
			if(rtunVal.contains("0"))
			{
				Sql = "Create TABLE YunBuyTable_"+hidGoodsID+hidCodeID;
				Sql += "(showCode TEXT(255) primary key,hidGoodsID_hidCodeID TEXT(255),Title TEXT(255),buyid TEXT(255),name TEXT(255),ip TEXT(255),buytime TEXT(255));";
				statement.executeUpdate(Sql); 
			}
			else
			{
				Sql =  "SELECT ";
				Sql += "FROM YunBuyTable_"+hidGoodsID+hidCodeID;
				Sql += "WHERE showCode='"+showCode+"'";
				rs = statement.executeQuery(Sql); 
				if(rs.next() == false)
				{
					OverGrep = true;
					return true;
				}
			}
			{
				Sql = "INSERT INTO YunBuyTable_"+hidGoodsID+hidCodeID;
				Sql += "(showCode,hidGoodsID_hidCodeID,Title,buyid,name,ip,buytime) VALUES (";
				Sql += 	showCode + ",";               //云购码
				Sql += 	hidGoodsID + hidCodeID +",";  //购买码
				Sql += 	Title +",";                   //标题
				Sql += 	buyid +",";		              //云购号
				Sql += 	name  +",";                   //用户名
				Sql += 	ip    +",";                   //用户IP
				Sql += 	time  +",";					  //购买时间
				Sql += ");";
				rs = statement.executeQuery(Sql); 
			}
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		finally{
			if(conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	public boolean NextPage(FinishLoadingEvent arg0)
	{
		DOMDocument domNew = arg0.getBrowser().getDocument();
		DOMElement g_pagination =  domNew.findElement(By.className("g-pagination"));
		
		
		DOMElement current = g_pagination.findElement(By.className("current"));
		DOMElement current_A = current.findElement(By.tagName("a"));
		String current_Index = current_A.getInnerText();
		List<DOMElement> emTotle = g_pagination.findElements(By.className("f-mar-left"));
		DOMElement em = null;
		for (DOMElement emSpan : emTotle)
		{
			em = emSpan.findElement(By.tagName("em"));
			if(em == null)
			{
				continue;
			}
			else
			{
				break;
			}
		}
		if(em == null){
			return false;
		}
		
		String Total_Index = em.getInnerText();
		
		if(current_Index.trim() == Total_Index.trim())
		{
			OverGrep = true;
			return true;
		}
		List<DOMElement> A_New =  domNew.findElements(By.tagName("a"));		
		for (DOMElement A_cur : A_New)
		{
			if(A_cur.getInnerText().contains("下一页"))
			{
				A_cur.click();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}
	public boolean NextPageByIndex(FinishLoadingEvent arg0)
	{
		DOMDocument domNew = arg0.getBrowser().getDocument();
		DOMElement div_allrecord = domNew.findElement(By.id("div_allrecord"));
		DOMElement div_g_pagination = div_allrecord.findElement(By.className("g-pagination"));
		DOMElement span_current = div_g_pagination.findElement(By.className("current"));
		DOMElement a_current = span_current.findElement(By.tagName("a"));
		String  strNowIndex = a_current.getInnerText();
		int iNowIndex  = Integer.parseInt(strNowIndex);
		
		return true;		
	}
	public boolean LoggingData(FinishLoadingEvent arg0)
	{
				DOMDocument domNew = arg0.getBrowser().getDocument();
								
				List<DOMElement> ulsNew =  domNew.findElements(By.tagName("ul"));
				for (DOMElement ult : ulsNew)
				{
					if(ult.getInnerHTML().contains("查看云购码") == false)
					{
						continue;
					}
					List<DOMElement> lisNew = ult.findElements(By.tagName("li"));
					String showCode;
					String hidGoodsID ;
					String hidCodeID ;
					String Title ;
					String buyid ;
					String name ;
					String ip ;
					String time;
					for(DOMElement liss :lisNew)
					{		   
						DOMElement hidGoodsIDEl = domNew.findElement(By.id("hidGoodsID"));
						DOMElement hidCodeIDEl = domNew.findElement(By.id("hidCodeID"));						
						DOMElement timeEl = liss.findElement(By.className("time"));
						DOMElement nameEl = liss.findElement(By.className("name"));
						//DOMElement peopleEl = liss.findElement(By.className("people"));						
						DOMElement ipEl = liss.findElement(By.className("ip"));
						DOMElement showCodeEl =  liss.findElement(By.name("showCode"));
						DOMElement o_titleEl = domNew.findElement(By.className("o-title"));
						//DOMElement o_titleEl_num = o_titleEl.findElement(By.className("num"));
						if(showCodeEl == null)
						{
							continue;
						}
						hidGoodsID = hidGoodsIDEl.getAttribute("value");
						hidCodeID = hidCodeIDEl.getAttribute("value");
						Title = o_titleEl.getInnerText(); //+ o_titleEl_num.getInnerHTML();
						buyid = showCodeEl.getAttribute("buyid");
						name = nameEl.getInnerText();
						//people = peopleEl.getInnerText();
						ip = ipEl.getInnerText();	
						time = timeEl.getInnerText();
						if(time.contains("时间"))
						{
							continue;
						}
						else
						{
							showCodeEl.click();
						}
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						DOMDocument domNewNew = arg0.getBrowser().getDocument();
						DOMElement code_ulEl = domNewNew.findElement(By.className("code-ul"));
						List<DOMElement> code_lis = code_ulEl.findElements(By.tagName("li"));
						for(DOMElement code_lisItem : code_lis)
						{
							try{
								FileWriter fw = new FileWriter("E:\\WorkSpace\\JsonPCracker_1yyg_ForJava\\log.txt",true);												
								showCode = code_lisItem.getInnerText();
								/*
								LoggingData_toDB(
										 showCode,
										 hidGoodsID, 
										 hidCodeID ,
										 Title ,
										 buyid ,
										 name ,
										 ip ,
										 time);
								*/
								fw.write(""+showCode.replace("\n", "")+" ,");
								fw.write(""+hidGoodsID.replace("\n", "")+" ,");
								fw.write(""+hidCodeID.replace("\n", "")+" ,");
								fw.write(""+Title.replace("\n", "")+" ,");
								fw.write(""+buyid.replace("\n", "")+" ,");
								fw.write(""+name.replace("\n", "")+" ,");
								fw.write(""+ip.replace("\n", "")+" ,");
								fw.write(""+time.replace("\n", "")+"");
								fw.write("\n");
								fw.close();
							}
							catch(Exception e)
							{
								
							}
						}
					}		            	
				}			
		return true;
	}
		
	@Override
	public void onFinishLoadingFrame(FinishLoadingEvent arg0)
	{
		OverGrep = false;
		while(true)
		{
			//点击界面清单
			if(OpenWebAndClickShowList(arg0) == false)
			{
				//错误处理
			}
			//检查是否有数据刷出来
			if(CheckDataGetOK(arg0) == true)
			{
				break;
			}
		}
		while(true)
		{
			try{
				FileWriter fw = new FileWriter("E:\\WorkSpace\\JsonPCracker_1yyg_ForJava\\log2.txt",true);												
				fw.write("LoggingData\n");
				fw.close();
			}
			catch(Exception e)
			{
				
			}
			if(LoggingData(arg0) == false)
			{
				break;
			}
			try{
				FileWriter fw = new FileWriter("E:\\WorkSpace\\JsonPCracker_1yyg_ForJava\\log2.txt",true);												
				fw.write("OverGrep\n");
				fw.close();
			}
			catch(Exception e)
			{
				
			}
			if(OverGrep == true)
			{
				break;
			}
			try{
				FileWriter fw = new FileWriter("E:\\WorkSpace\\JsonPCracker_1yyg_ForJava\\log2.txt",true);												
				fw.write("NextPage\n");
				fw.close();
			}
			catch(Exception e)
			{
				
			}
			if(NextPage(arg0) == false)
			{
				break;
			}
			try{
				FileWriter fw = new FileWriter("E:\\WorkSpace\\JsonPCracker_1yyg_ForJava\\log2.txt",true);												
				fw.write("OverGrep\n");
				fw.close();
			}
			catch(Exception e)
			{
				
			}
			try{
				FileWriter fw = new FileWriter("E:\\WorkSpace\\JsonPCracker_1yyg_ForJava\\log2.txt",true);												
				fw.write("CheckDataGetOK\n");
				fw.close();
			}
			catch(Exception e)
			{
				
			}
		}
		try{
			FileWriter fw = new FileWriter("E:\\WorkSpace\\JsonPCracker_1yyg_ForJava\\log2.txt",true);												
			fw.write("END\n");
			fw.close();
		}
		catch(Exception e)
		{
			
		}
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
