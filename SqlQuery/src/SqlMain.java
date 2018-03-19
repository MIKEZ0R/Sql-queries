import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.ArrayList;


public class SqlMain {

	private JFrame frame;
	private CardLayout cl;
	private JPanel cardPanel;
	private JPanel mainPanel;
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JPanel centerPanel;
	private JPanel upperCenter;
	private JScrollPane vertical;
	private JTextArea query;
	private JTextArea output;
	private JButton a;
	private JButton b;
	private JButton q;
	private JButton clear;
	private JButton d;
	private JButton e;
	private ArrayList<String> colLabels;
	private ArrayList<String> finalString;
	private JTextArea stats;
	private JTable table;
	//private Object[] columnNames;
	//private Object[][] rowData;
	private DefaultTableModel model;
	private Object[] columnNames;
	private Object[][] rowData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SqlMain window = new SqlMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SqlMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Sql Queries");
		frame.setBounds(100, 100, 1024, 720);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		cardPanel = new JPanel();
		cl = new CardLayout();
		cardPanel.setLayout(cl);
		mainPanel = new JPanel();
		upperPanel = new JPanel();
		lowerPanel = new JPanel();
		centerPanel = new JPanel();
		upperCenter = new JPanel();
		stats = new JTextArea();
		stats.setEditable(false);
		query = new JTextArea("select * from Aktorzy");
		output = new JTextArea("output");
		d = new JButton();
		d.setBackground(Color.black);
		d.setEnabled(false);
		d.setPreferredSize(new Dimension(100,35));
		e = new JButton();
		e.setEnabled(false);
		e.setBackground(Color.black);
		e.setPreferredSize(new Dimension(100,35));
		a = new JButton(":)");
		a.setEnabled(false);
		a.setBackground(Color.BLACK);
		a.setFont(new Font("Tahoma", Font.BOLD, 150));
		b = new JButton(":)");
		b.setEnabled(false);
		b.setBackground(Color.BLACK);
		b.setFont(new Font("Tahoma", Font.BOLD, 150));
		q = new JButton("Query");
		q.setBackground(Color.BLACK);
		q.setFont(new Font("Tahoma", Font.BOLD, 50));
		q.setForeground(Color.WHITE);
		colLabels = new ArrayList<String>();
		finalString = new ArrayList<String>();
		clear = new JButton("Clear");
		clear.setBackground(Color.BLACK);
		clear.setFont(new Font("Tahoma", Font.BOLD, 50));
		clear.setForeground(Color.WHITE);
		lowerPanel.setLayout(new GridLayout(0,2,1,1));
		lowerPanel.add(q);
		lowerPanel.add(clear);
		centerPanel.setLayout(new BorderLayout());
		upperCenter.setLayout(new GridLayout(0,3,1,1));
		upperCenter.add(d);
		upperCenter.add(stats);
		upperCenter.add(e);
		upperCenter.setVisible(false);
		//Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
		//        { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
	   // Object columnNames[] = { "Column One", "Column Two", "Column Three" };
		table = new JTable();
		Border border = BorderFactory.createLineBorder(Color.RED);
	    query.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		//table.setEnabled(false);
	    output.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    vertical = new JScrollPane(table);
	    centerPanel.add(vertical,BorderLayout.CENTER);
	    centerPanel.add(upperCenter,BorderLayout.NORTH);
	    vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(lowerPanel,BorderLayout.SOUTH);
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		//mainPanel.add(output,BorderLayout.CENTER);
		//mainPanel.add(c,BorderLayout.SOUTH);
		
		upperPanel.setLayout(new BoxLayout(upperPanel,BoxLayout.X_AXIS));
		upperPanel.add(a);
		upperPanel.add(query);
		upperPanel.add(b);
		
		mainPanel.add(upperPanel,BorderLayout.NORTH);
		
		cardPanel.add(mainPanel,"main");
		
	
		
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				output.setText("");
				finalString.clear();
				colLabels.clear();
				Object[][] nothing1 = new Object[0][0];
				Object[][] nothing2 = new Object[0][0];
				model = new DefaultTableModel(nothing1, nothing2);
				table.setModel(model);
				table.setEnabled(false);
				stats.setText("");
				upperCenter.setVisible(false);
			}
		});
		
		
		q.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				output.setText("");
				try{
					//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Grzegorz\\Desktop\\WypozyczalniaDVD.mdb");
					
					Statement st = conn.createStatement();
					
					String sql =query.getText();
					ResultSet rs = st.executeQuery(sql);
					ResultSetMetaData rsmd = rs.getMetaData();

					
					int howMany = 1;
					int k = 0;
					while(k < rsmd.getColumnCount()){
						k++;
						colLabels.add(rsmd.getColumnName(k));
					}
					Object columnNames[] = new Object[colLabels.size()];
					
					for(int i=0;i<columnNames.length;i++){
						columnNames[i] = colLabels.get(i);
						System.out.println(columnNames[i]);
					}
					while(rs.next()){
						for(int i=1;i<=rsmd.getColumnCount();i++){
							finalString.add(rs.getString(i));
						}
						howMany++;
						
					}
					System.out.println(finalString.size()/columnNames.length);
					Object rowData[][] = new Object[finalString.size()/columnNames.length][columnNames.length];
					for(int i=0;i<rowData.length;i++){
						for(int j=0;j<columnNames.length;j++){
							rowData[i][j] = finalString.get(0);
							finalString.remove(0);
						}
						System.out.println();
					}
					
					model = new DefaultTableModel(rowData, columnNames);
					table.setModel(model);
					table.setEnabled(false);
					table.setRowHeight(20);
					table.setFont(new Font("Tahoma", Font.PLAIN, 13));
					table.setForeground(Color.BLACK);
					stats.setText("Number of rows: "+table.getRowCount()+"\n"+"Number of columns: "+table.getColumnCount());
					upperCenter.setVisible(true);
					vertical.repaint();
					vertical.revalidate();

					
					}catch(Exception e){
						Object[] errorCol= {"ERROR"};
						Object[][] err = {{"  Wrong query or query cannot be executed on this data base !"}};
						model = new DefaultTableModel(err, errorCol);
						table.setModel(model);
						table.setRowHeight(50);
						table.setFont(new Font("Tahoma", Font.BOLD, 32));
						table.setForeground(Color.red);
						table.setEnabled(false);
						
					}
			
				}
		});
		
		frame.getContentPane().add(cardPanel);
		
	}

}
