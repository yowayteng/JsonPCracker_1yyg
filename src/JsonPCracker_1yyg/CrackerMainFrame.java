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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


public class CrackerMainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3758518016242710046L;
	private JPanel contentPane;
	private JTextField textField; 

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

	final int BROWSER_COUNT = 10;
	private Browser[] browser;
	/**
	 * Create the frame.
	 */
	public CrackerMainFrame() 
	{
		iswebload = false;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 900);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);		 
		GridBagLayout layout = new GridBagLayout();
		contentPane.setLayout(layout);
		
		JPanel ContentBrowserPanel = new JPanel();
		ContentBrowserPanel.setLayout(new GridLayout(5,2));
		browser = new Browser[BROWSER_COUNT];
		JPanel[] showpanel = new JPanel[BROWSER_COUNT];
		int BROWSER_ID = 0;
		for(JPanel showpanel_item : showpanel)
		{		
			showpanel_item = new JPanel();	
			browser[BROWSER_ID] = new Browser();
			BrowserView view= new BrowserView(browser[BROWSER_ID]);
			Web_LoadListener browser_LoadListener = new Web_LoadListener(BROWSER_ID,BROWSER_COUNT);
			browser[BROWSER_ID].addLoadListener(browser_LoadListener);
			showpanel_item.setLayout(new BorderLayout());
			view.setBorder(new LineBorder(new Color(0, 0, 0)));
			showpanel_item.add(view,BorderLayout.CENTER);
			ContentBrowserPanel.add(showpanel_item);
			BROWSER_ID++;
		}	
		contentPane.add(ContentBrowserPanel);
			
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
		contentPane.add(toolspanel);	
		
		GridBagConstraints s= new GridBagConstraints();
		s.fill = GridBagConstraints.BOTH;
		s.gridwidth=0;
		s.weightx = 1;
		s.weighty=1;
		layout.setConstraints(ContentBrowserPanel, s);
		s.gridwidth=0;
		s.weightx = 0;
		s.weighty=0;
		layout.setConstraints(toolspanel, s);
	}
	 
	Boolean Load_A_NewWeb(String url)
	{
		setIswebload(false);
		//0~9号线程，每回加10
		for(int BROWSER_ID = 0;BROWSER_ID < BROWSER_COUNT;BROWSER_ID++)
		{
			browser[BROWSER_ID].loadURL(url);
		}
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
