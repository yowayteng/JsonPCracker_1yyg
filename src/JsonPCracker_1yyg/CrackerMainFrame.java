package JsonPCracker_1yyg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;


public class CrackerMainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3758518016242710046L;
	private JPanel contentPane;
	private JTextField textField;
	private Browser browser;
	private BrowserView view;
	private Web_LoadListener browser_LoadListener;
	private Web_ScriptContextListener browser_ScriptContextListener;
	private Web_StatusListener browser_StatusListener;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrackerMainFrame frame = new CrackerMainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CrackerMainFrame() 
	{
		iswebload = false;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 500);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);		 

		
		browser = new Browser();
		view = new BrowserView(browser);
		JPanel showpanel = new JPanel();
		showpanel.setLayout(new BorderLayout());
		view.setBorder(new LineBorder(new Color(0, 0, 0)));
		showpanel.add(view,BorderLayout.CENTER);
		
		JPanel toolspanel = new JPanel();
		toolspanel.setLayout(new BorderLayout(0, 0));
		JPanel nevgatepanel = new JPanel();		
		toolspanel.add(nevgatepanel,BorderLayout.NORTH);
		nevgatepanel.setLayout(new BoxLayout(nevgatepanel, BoxLayout.X_AXIS));
		nevgatepanel.add(new JLabel("输入路径"));		
		textField = new JTextField();
		textField.setColumns(50);
		textField.setText("http://www.1yyg.com/products/23074.html");
		nevgatepanel.add(textField);
		
		JButton StartButton = new JButton("开始");
		StartButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String url = textField.getText();
				Load_A_NewWeb(url);
			}
		});
		nevgatepanel.add(StartButton);
		
		contentPane.setLayout(new GridLayout(2, 0, 0, 0));
		contentPane.add(showpanel);
		contentPane.add(toolspanel);
		
		browser_LoadListener = new Web_LoadListener();
		browser.addLoadListener(browser_LoadListener);
		browser_ScriptContextListener = new Web_ScriptContextListener();
		browser.addScriptContextListener(browser_ScriptContextListener);
		browser_StatusListener = new Web_StatusListener();
		browser.addStatusListener(browser_StatusListener);
	}
	 
	Boolean Load_A_NewWeb(String url)
	{
		setIswebload(false);
		browser.loadURL(url);	
		return true;
	}
	
	private Boolean iswebload;
	public Boolean getIswebload() {
		return iswebload;
	}

	public void setIswebload(Boolean iswebload) {
		this.iswebload = iswebload;
	}

}
