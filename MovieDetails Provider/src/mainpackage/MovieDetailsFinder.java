package mainpackage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class MovieDetailsFinder extends JFrame implements ActionListener
{

	private JPanel contentPane;
	
	private JButton search;
	private JLabel movieimagelabel,label2;
	static JLabel noconnectionlabel;
	private String moviename="noimage",movielink,movieimagelink,moviepagelink;
	private boolean getmovielink=false,getmovieimagelink=false,getmoviepagelink=false,getdetails=false;
	private JTextField movienamefield;
	private JTextArea details;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MovieDetailsFinder frame = new MovieDetailsFinder();
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
	public MovieDetailsFinder() {
		setTitle("MovieDetails Finder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 10, 1165,680);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(0, 191, 255), 20));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		noconnectionlabel = new JLabel("");
		noconnectionlabel.setBounds(297, 217, 142, 117);
		noconnectionlabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		noconnectionlabel.setVisible(false);
		noconnectionlabel.setIcon(new ImageIcon("./noconnection.png"));
		contentPane.add(noconnectionlabel);
		
		label2 = new JLabel("No Internet Connection");
		label2.setBounds(241, 357, 221, 28);
		label2.setFont(new Font("Microsoft PhagsPa", Font.PLAIN, 20));
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setVisible(false);
		contentPane.add(label2);
		
		 movieimagelabel = new JLabel("No image");
		 movieimagelabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		 movieimagelabel.setBackground(new Color(245, 245, 220));
		 movieimagelabel.setToolTipText("Movie image");
		movieimagelabel.setHorizontalAlignment(SwingConstants.CENTER);
		movieimagelabel.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		movieimagelabel.setOpaque(true);
		movieimagelabel.setBounds(765, 109, 353, 500);
		contentPane.add(movieimagelabel);
		
		
		
		 search = new JButton("");
		 search.setToolTipText("search");
		search.setBackground(new Color(0, 191, 255));
		search.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		search.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		search.setBounds(1060, 50, 58, 48);
		search.setIcon(new ImageIcon("./search.png"));
		search.setFocusable(false);
		contentPane.add(search);
		
	
		
		
		JLabel title = new JLabel("Movie Details Finder");
		title.setForeground(new Color(255, 255, 255));
		title.setBackground(new Color(0, 191, 255));
		title.setFont(new Font("Segoe Print", Font.BOLD, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 0, 1174, 39);
		title.setOpaque(true);
		contentPane.add(title);
	
		details = new JTextArea();
		details.setBackground(new Color(245, 255, 250));
		details.setEditable(false);
		details.setBounds(25, 50, 730, 560);
		details.setBorder(new LineBorder(new Color(192, 192, 192), 3));
		contentPane.add(details);
		details.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		JScrollPane sc=new JScrollPane(details,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sc.setBounds(25, 50, 730, 560);
		sc.getVerticalScrollBar().setPreferredSize(new Dimension(10,0));
		sc.getHorizontalScrollBar().setPreferredSize(new Dimension(0,10));
		contentPane.add(sc);
		search.addActionListener(this);
		this.getRootPane().setDefaultButton(search);
		
		movienamefield = new JTextField();
		movienamefield.setText("Search");
		movienamefield.setHorizontalAlignment(SwingConstants.LEFT);
		movienamefield.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		movienamefield.setBounds(766, 51, 281, 48);
		contentPane.add(movienamefield);
		movienamefield.setColumns(10);
		movienamefield.setBorder(new LineBorder(new Color(192, 192, 192), 3));
		movienamefield.setFocusable(true);
		
		
		 this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(final WindowEvent windowenent) 
	            {
	            	if(moviename!="noimage")
					{
					File deleteimage=new File("./"+moviename+".jpg");
					deleteimage.delete();
					}
	            }
	        });
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		CreateProgressBar p;
		
		if(checkconnection())
		{
			
			noconnectionlabel.setVisible(false);
			label2.setVisible(false);
			if(e.getSource()==search)
			{
			
				
				try
				{
					
					details.setText("");
					movieimagelabel.setText("");
					this.setCursor(Cursor.WAIT_CURSOR);
					if(moviename!="noimage")
					{
					File deleteimage=new File("./"+moviename+".jpg");
					deleteimage.delete();
					}
					
					moviename=movienamefield.getText();
					
					getmovielink=true;
			
				
				
				
				}
				catch(Exception exp)
				{
					details.setText("No result found...!");
					this.setCursor(Cursor.DEFAULT_CURSOR);
				}
			}
		

		}
		else	
		{
			noconnectionlabel.setVisible(true);
			label2.setVisible(true);
			details.setText("");
			movieimagelabel.setText("No image");
		}
		if(getmovielink)
		{
			 movielink=DataProvider.getMovielink(moviename);
			 getmovielink=false;
			 getdetails=true;
			 repaint();
		}
		if(getdetails)
		{
			try
			{
				managedetails(DataProvider.details(movielink));
				getmovieimagelink=true;
				getdetails=false;
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				details.setText("No result found...!");
			}
			repaint();
		}
		
		if(getmovieimagelink)
		{
			try
			{
				String movieimagelink=DataProvider.getMoviePoster(movielink);
				movieimagelink=movieimagelink.substring(0,movieimagelink.indexOf("@"))+"@._V1_QL50_SY500_CR0,0,347,500_AL_.jpg";
				DataProvider.downloadimage(movieimagelink,moviename,movielink);
				movieimagelabel.setIcon(new ImageIcon("./"+moviename+".jpg"));
			}
			catch(Exception exp)
			{
				

				 try
				 {
					 
					movieimagelink=DataProvider.getMoviePoster(movielink);
				 	URL url = new URL(movieimagelink);
			        BufferedImage img1 = ImageIO.read(url);
			        BufferedImage img=DataProvider.resizeImage(img1,362,500);
			        File file = new File("./"+moviename+".jpg");
			        ImageIO.write(img, "jpg", file);
			        System.out.println("Picture downloaded succesfully");
			        movieimagelabel.setIcon(new ImageIcon("./"+moviename+".jpg"));
				 }
				 catch(Exception ex)
				 {
					 try
					 {
					 movieimagelink=DataProvider.getMoviePoster("https://umkarsingh.com/wp-content/uploads/2018/10/No-image-available.jpg");
					 	URL url = new URL(movieimagelink);
				        BufferedImage img1 = ImageIO.read(url);
				        BufferedImage img=DataProvider.resizeImage(img1,362,500);
				        File file = new File("./"+moviename+".jpg");
				        ImageIO.write(img, "jpg", file);
				        System.out.println("Picture downloaded succesfully");
				        movieimagelabel.setIcon(new ImageIcon("./"+moviename+".jpg"));
					 }
					 catch(Exception exp2)
					 {
						 movieimagelabel.setText("No image found");
					 }
				 }
			}
			this.setCursor(Cursor.DEFAULT_CURSOR);
		}
	
		
		
	}
	public void managedetails(String detailsdata)
	{
//		detailsdata.repalce();
	
		int gape[]=new int[4];
		int index=0;
		for(int i=0; i<detailsdata.length()&&index<4; i++)
		{
			if(detailsdata.charAt(i)=='|')
			{
				gape[index]=i;
				index++;
			}
		}
		if(detailsdata.contains("Movie Title"))
		{
			String title=detailsdata.substring(detailsdata.indexOf("Movie Title"),detailsdata.indexOf("\n",detailsdata.indexOf("Movie Title")));
		details.setText(title+"\n\n");
		}
		if(detailsdata.contains("IMDb Rating"))
		{
		String rating=detailsdata.substring(detailsdata.indexOf("IMDb Rating"),detailsdata.indexOf("\n",detailsdata.indexOf("IMDb Rating")));
		details.append(rating+"\n\n");
		}
		if(detailsdata.contains("Movie Duraction time"))
		{
		String runningtime=detailsdata.substring(detailsdata.indexOf("Movie Duraction time"),detailsdata.indexOf("\n",detailsdata.indexOf("Movie Duraction time")));
		details.append(runningtime+"\n\n");
		}
		if(gape[1]!=0 && gape[2]!=0)
		{
		  String movietype="Movie type   :   "+detailsdata.substring(gape[1]+1,gape[2]);
		details.append(movietype+"\n\n");
		}
		if(detailsdata.contains("Country"))
		{
		    String country="Country   :   "+detailsdata.substring(detailsdata.indexOf("Country")+8,detailsdata.indexOf("\n",detailsdata.indexOf("Country")));
		details.append(country+"\n\n");
		}
		if(detailsdata.contains("Release Date"))
		{
		String releasedate="Release  Date   :   "+detailsdata.substring(detailsdata.indexOf("Release Date")+13,detailsdata.indexOf("\n",detailsdata.indexOf("Release Date")));
		if(releasedate.contains(" See "))
		{
			releasedate=releasedate.substring(0, releasedate.indexOf(" See "));
		}
		details.append(releasedate+"\n\n");
		}
		if(detailsdata.contains("Filming Locations"))
		{
		String filminglocation="Filming Location   :   "+detailsdata.substring(detailsdata.indexOf("Filming Locations")+18,detailsdata.indexOf("\n",detailsdata.indexOf("Filming Locations")));
		if(filminglocation.contains(" See "))
		{
			filminglocation=filminglocation.substring(0, filminglocation.indexOf(" See ")-1);
		}
		details.append(filminglocation+"\n\n");
		}
		
		if(detailsdata.contains("Budget"))
		{
			String budget="Budget   :   "+detailsdata.substring(detailsdata.indexOf("Budget")+7,detailsdata.indexOf("\n",detailsdata.indexOf("Budget")));
		 details.append(budget+"\n\n");
		}
		if(detailsdata.contains("Cumulative Worldwide Gross"))
		{
			String worldwidegross="Cumulative Worldwide Gross   :   "+detailsdata.substring(detailsdata.indexOf("Cumulative Worldwide Gross")+"Cumulative Worldwide Gross:".length(),detailsdata.indexOf("\n",detailsdata.indexOf("Cumulative Worldwide Gross")));
		details.append(worldwidegross+"\n\n");
		}
		if(detailsdata.contains("Production Co"))
		{
			String productionco="Production Co   :   "+detailsdata.substring(detailsdata.indexOf("Production Co")+"Production Co".length()+1,detailsdata.indexOf("See more",detailsdata.indexOf("Production Co")));
			if(productionco.contains(" See "))
			{
				productionco=productionco.substring(0, productionco.indexOf(" See ")-1);
			}
			details.append(productionco+"\n\n");
		}
		if(detailsdata.contains("Director") )
		{
				String directors=   "Directors   :   "+detailsdata.substring(detailsdata.indexOf("Director")+9,detailsdata.indexOf("\n",detailsdata.indexOf("Director")));
				if(directors.contains(" See "))
				{
					directors=directors.substring(0, directors.indexOf(" See ")-1);
				}	
				details.append(directors+"\n\n");
			
		}
		if(detailsdata.contains("Writer"))
		{
			
				String writers="Writers   :   "+detailsdata.substring(detailsdata.indexOf("Writer")+8,detailsdata.indexOf("\n",detailsdata.indexOf("Writer")));
				if(writers.contains(" See "))
				{
					writers=writers.substring(0, writers.indexOf(" See ")-1);
				}	
				details.append(writers+"\n\n");
			
		}
		if(detailsdata.contains("Star"))
		{
		String stars="Stars   :   "+detailsdata.substring(detailsdata.indexOf("Star")+6,detailsdata.indexOf("\n",detailsdata.indexOf("Star")));
		if(stars.contains(" See "))
		{
			stars=stars.substring(0,stars.indexOf(" See ")-1);
		}	
		details.append(stars+"\n\n");
		}
		if(detailsdata.contains("Story"))
		{
			
				String summary=detailsdata.substring(detailsdata.indexOf("Story"),detailsdata.indexOf("\n",detailsdata.indexOf("Story")));
				details.append(summary+"\n");
			
		}
		
	details.setCaretPosition(0);	
	}
	public boolean checkconnection()
	{
		try
		{
		URL url=new URL("https://www.google.com");
		URLConnection connection=url.openConnection();
		connection.connect();
		return true;
		}
		catch (MalformedURLException e) {
	         return false;
	      } catch (IOException e) {
	         return false;
	      }
		
	
	}
}
class CreateProgressBar extends JDialog 
{

	private JPanel contentpane;
	public static void main(String args[])
	{
		CreateProgressBar cpg=new CreateProgressBar("Fetching data",22);
		cpg.setLocation(100,100);
	}
	public CreateProgressBar(String text,int progress)
	{
		setSize(400,180);
		setTitle("Progress Information");
		
		contentpane=new JPanel();
		this.setResizable(false);
		this.setBackground(Color.gray);
		contentpane.setLayout(null);
		this.setContentPane(contentpane);
		
		JLabel label=new JLabel(text);
		label.setBounds(0, 20, 400, 50);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Arial",Font.PLAIN,17));
		contentpane.add(label);
		
		
		JProgressBar progressbar=new JProgressBar();
		progressbar.setBounds(20, 80, 340, 25);
		progressbar.setForeground(Color.green);
		progressbar.setBackground(Color.white);
		progressbar.setStringPainted(true);
		progressbar.setValue(progress);
		contentpane.add(progressbar);
	}
	
	 
		
}