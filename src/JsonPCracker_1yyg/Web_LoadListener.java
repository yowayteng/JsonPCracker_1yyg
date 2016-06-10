package JsonPCracker_1yyg;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
	
	final int SLEEP_1 = 500;
	final int SLEEP_2 = 100;
	FileWriter fw;
	public Web_LoadListener(int inBROWSER_ID,int inBROWSER_COUNT) {
		super();
		// TODO Auto-generated constructor stub
		BROWSER_ID = inBROWSER_ID;
		BROWSER_COUNT = inBROWSER_COUNT;
		bOnprocess = false;
		status = 0;
		try {
			fw = new FileWriter("E:\\WorkSpace\\"+inBROWSER_ID +".csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	protected void finalize() throws java.lang.Throwable {
		fw.close();
        super.finalize();  
     }

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
	@Override
	public void onProvisionalLoadingFrame(ProvisionalLoadingEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onStartLoadingFrame(StartLoadingEvent arg0) {
		// TODO Auto-generated method stub
	}
	/*
	 * 点击所有参与记录
	 */
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
					Thread.sleep(SLEEP_1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}
	/*
	 * 确认显示出云购记录 
	 */
	public boolean CheckDataGetOK(FinishLoadingEvent arg0)
	{
		DOMDocument domNew = arg0.getBrowser().getDocument();
		DOMElement div_allrecord =  domNew.findElement(By.id("div_allrecord"));
		if(div_allrecord.getInnerHTML().contains("查看云购码") == true)
		{
			return true;
		}
		return false;
	}
	
	public int CountByBuyId(
			String hidGoodsID ,
			String hidCodeID ,
			String buyid)
	{
		String url = "jdbc:mysql://127.0.0.1:3306/db_1yyg";
		String username = "5105243920";
		String password = "spdh123";
		Connection conn = null;  
		int Count = -1;
		try {
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Count;
		}
		ResultSet rs = null;
		java.sql.Statement statement;
		String Sql = "";
		try {
			conn = DriverManager.getConnection(url,username,password);			
			statement = conn.createStatement();	
			{
				Sql =  "Select count(*) FROM YunBuyTable_"+hidGoodsID+hidCodeID+" ";
				Sql += "WHERE buyid='" + buyid + "'";
				rs = statement.executeQuery(Sql);
				if(rs.next()  == false)
				{
					return Count;
				}
				else
				{
					Count = rs.getInt(1);
				}
			}			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Count;
		} 
		finally{
			if(conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return Count;
				}
			}
		}
		return Count;
	}
	/*
	 * 将数据录入到数据库
	 */
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
		String url = "jdbc:mysql://127.0.0.1:3306/db_1yyg";
		String username = "5105243920";
		String password = "spdh123";
		Connection conn = null;  
		try {
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		ResultSet rs = null;
		java.sql.Statement statement;
		String Sql = "";
		try {
			conn = DriverManager.getConnection(url,username,password);			
			statement = conn.createStatement();		
			//如果没有表建新表
			{
				Sql  = "create  table if not exists YunBuyTable_"+hidGoodsID+hidCodeID+"(";
				Sql += "`showCode` int(11) NOT NULL,";
				Sql += "`hidGoodsID` varchar(45) DEFAULT NULL,";
				Sql += "`hidCodeID` varchar(45) DEFAULT NULL,";
				Sql += "`Title` varchar(255) DEFAULT NULL,";
				Sql += "`buyid` varchar(45) DEFAULT NULL,";
				Sql += "`name` varchar(45) DEFAULT NULL,";
				Sql += "`ip` varchar(45) DEFAULT NULL,";
				Sql += "`buytime` varchar(45) DEFAULT NULL,";
				Sql += "PRIMARY KEY (`showCode`)";
				Sql += ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
				statement.executeUpdate(Sql); 
			}
			//查询数据是否重复
			{
				Sql =  "Select * FROM YunBuyTable_"+hidGoodsID+hidCodeID+" ";
				Sql += "WHERE showCode='" + showCode + "'";
				rs = statement.executeQuery(Sql);
				if(rs.next() == true)
				{
					//OverGrep = true;
					return true;
				}
				else
				{
					OverGrep = false;
				}
			}
			//插入数据
			{
				Sql = "INSERT INTO YunBuyTable_"+hidGoodsID+hidCodeID;
				Sql += "(showCode,hidGoodsID,hidCodeID,Title,buyid,name,ip,buytime) VALUES (";
				Sql += 	"\""+showCode   +"\",";                //云购码
				Sql += 	"\""+hidGoodsID +"\",";               //购买码
				Sql += 	"\""+hidCodeID 	+"\",";                //购买码
				Sql += 	"\""+Title 		+"\",";                   //标题
				Sql += 	"\""+buyid 		+"\","; 		              //云购号
				Sql += 	"\""+name  		+"\",";                   //用户名
				Sql += 	"\""+ip    		+"\",";                   //用户IP
				Sql += 	"\""+time  		+"\"";				  //购买时间
				Sql += ");";
				statement.executeUpdate(Sql); 
			}
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		finally{
			if(conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	/*
	 * 翻页函数（指定跳转位置）
	 */
	public boolean NextPageByIndex(FinishLoadingEvent arg0)
	{
		int iNowIndex2 = 0;
		DOMDocument domNew = arg0.getBrowser().getDocument();
		DOMElement div_allrecord = domNew.findElement(By.id("div_allrecord"));
		DOMElement div_g_pagination = div_allrecord.findElement(By.className("g-pagination"));
		DOMElement span_current = div_g_pagination.findElement(By.className("current"));
		DOMElement  total_em = div_g_pagination.findElement(By.tagName("em"));
		DOMElement  input_box;
		input_box = div_g_pagination.findElement(By.tagName("input"));
		DOMElement  a_boutton;
		a_boutton = div_g_pagination.findElement(By.id("btnGotoPage"));
		DOMElement a_current = span_current.findElement(By.tagName("a"));
		String  strNowIndex = a_current.getInnerText();
		String  strTotalIndex = total_em.getInnerText();
		int iNowIndex  = Integer.parseInt(strNowIndex);
		int iTotalIndex  = Integer.parseInt(strTotalIndex);
		//如果第一次
		if(Last_Index == 0)
		{		
			iNowIndex = BROWSER_ID + 1;
		}
		//不是第一次
		else if(Last_Index == iNowIndex)
		{
			iNowIndex = iNowIndex + BROWSER_COUNT;
		}
		else if(Last_Index > iNowIndex)
		{
			iNowIndex = Last_Index;
		}

		//如果超出则不跳转
		if(iNowIndex > iTotalIndex)
		{
			OverGrep = true;
			return true;
		}
		do
		{
			input_box.setAttribute("value", ""+ iNowIndex);
			a_boutton.click();
			try {
				Thread.sleep(SLEEP_1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DOMDocument domNew2 = arg0.getBrowser().getDocument();
			DOMElement div_allrecord2 = domNew2.findElement(By.id("div_allrecord"));
			DOMElement div_g_pagination2 = div_allrecord2.findElement(By.className("g-pagination"));
			DOMElement span_current2 = div_g_pagination2.findElement(By.className("current"));
			DOMElement a_current2 = span_current2.findElement(By.tagName("a"));
			String  strNowIndex2 = a_current2.getInnerText();
			iNowIndex2 = Integer.parseInt(strNowIndex2);
			input_box = div_g_pagination2.findElement(By.tagName("input"));
			a_boutton = div_g_pagination2.findElement(By.id("btnGotoPage"));
		}
		while( iNowIndex != iNowIndex2 || CheckDataGetOK(arg0) == false);
		Last_Index = iNowIndex;	
		LocalDateTime c = LocalDateTime.now();
		try {
			fw.write(iNowIndex + ","+c.toString() +"\n");
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;		
	}
	
	/*
	 * 查找云购码的内容，然后录入到数据库
	 */
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
							Thread.sleep(SLEEP_2);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						DOMDocument domNewNew = arg0.getBrowser().getDocument();
						DOMElement code_ulEl = domNewNew.findElement(By.className("code-ul"));
						List<DOMElement> code_lis = code_ulEl.findElements(By.tagName("li"));
						if(code_lis.size() != CountByBuyId(hidGoodsID,hidCodeID,buyid))
						{
							for(DOMElement code_lisItem : code_lis)
							{
								try{
									showCode = code_lisItem.getInnerText();							
									LoggingData_toDB(
											 showCode,
											 hidGoodsID, 
											 hidCodeID ,
											 Title ,
											 buyid ,
											 name ,
											 ip ,
											 time);	
									/*
									FileWriter fw = new FileWriter("E:\\WorkSpace\\JsonPCracker_1yyg_ForJava\\log.txt",true);												
						
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
									*/
								}
								catch(Exception e)
								{
									
								}
							}
						}
					}		            	
				}			
		return true;
	}
		
	boolean OverGrep;
	public int BROWSER_ID;
	public int BROWSER_COUNT;
	public int Last_Index;
	public boolean bOnprocess;
	public int status;
	
	@Override
	public void onFinishLoadingFrame(FinishLoadingEvent arg0)
	{
		if(bOnprocess)
		{
			return;
		}
		else
		{
			bOnprocess = true;
		}
		OverGrep = false;
		while(true)
		{
			try
			{
				do
				{
					//点击界面清单
					if(OpenWebAndClickShowList(arg0) == false)
					{
						//错误处理
						arg0.getBrowser().reload();
						bOnprocess = false;
						return;
					}
				}
				//直到显示出用户数据
				while( CheckDataGetOK(arg0) == false);
				//显示下一页
				NextPageByIndex(arg0);
				if(OverGrep == true)
				{
					return;
				}
				LoggingData(arg0);
				if(OverGrep == true)
				{
					return;
				}
			}
			catch(Exception e)
			{	
				//.............
				arg0.getBrowser().reload();
				bOnprocess = false;
				return;
			}
		}
	}
	
}
