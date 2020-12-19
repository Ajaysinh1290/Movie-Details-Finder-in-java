package mainpackage;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataProvider {
	
	public static String getMovielink(String moviename) 
	{
		StringBuffer movielink=new StringBuffer();
		try
		{
	
		moviename=moviename.replace(" ", "+");
		String url="https://www.imdb.com/find?q="+moviename+"&ref_=nv_sr_sm";
		Document doc=Jsoup.connect(url).get();
		Elements links=doc.select(".result_text>a[href]");
		  for (Element link : links) {  
            
              if(link.attr("href").contains("fn_al_tt_1"))
              {
            	  movielink.append("https://www.imdb.com"+link.attr("href"));
              }
          } 
		}
		catch(Exception e)
		{
			movielink.append("null");
		}
		 return movielink.toString();
	}
	
	public static String  getMoviePoster(String link) throws Exception
	{
		StringBuffer url=new StringBuffer();
		try
		{
		Document doc=Jsoup.connect(link).get();
		Elements e=doc.select(".poster");

		e.forEach(ele->
		{
			Element image=ele.select("img").first();
			 url.append(image.absUrl("src"));
			
			
		});
		}
		catch(Exception e)
		{
			url.append("https://umkarsingh.com/wp-content/uploads/2018/10/No-image-available.jpg");
		}
		return url.toString();
			
	}


	public static void downloadimage(String imagelink,String name,String movielink) throws Exception
	{
		try
		{
		URL url = new URL(imagelink);
        BufferedImage img = ImageIO.read(url);
        File file = new File(".\\"+name+".jpg");
        ImageIO.write(img, "jpg", file);
        System.out.println("Picture downloaded succesfully");
		}
		 catch(MalformedURLException exp)
		 {
			 try
			 {
				 
				imagelink=getMoviePoster(movielink);
			 	URL url = new URL(imagelink);
		        BufferedImage img1 = ImageIO.read(url);
		        BufferedImage img=resizeImage(img1,362,500);
		        File file = new File(".\\"+name+".jpg");
		        ImageIO.write(img, "jpg", file);
		        System.out.println("Picture downloaded succesfully");
			 }
			 catch(MalformedURLException ex)
			 {
				 imagelink=getMoviePoster("https://umkarsingh.com/wp-content/uploads/2018/10/No-image-available.jpg");
				 	URL url = new URL(imagelink);
			        BufferedImage img1 = ImageIO.read(url);
			        BufferedImage img=resizeImage(img1,362,500);
			        File file = new File(".\\"+name+".jpg");
			        ImageIO.write(img, "jpg", file);
			        System.out.println("Picture downloaded succesfully");
			 }
		 }
		 catch(IOException e)
		 {
			 
		 }
	
	}
	public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
	
//	
	public static String details(String movielink) throws Exception
	{
		Document doc=Jsoup.connect(movielink).get();
	
		StringBuffer Details=new StringBuffer();
		Elements titlebar=doc.select("div.title_block");
		System.out.println(doc.select("h4.status-message-heading").text());
		if(!doc.select("h4.status-message-heading").text().contains("Coming Soon"))
		{
			for(Element element :titlebar)
			{
				Details.append(element.text()+"\n\n");
				
				
			}
		}
		else
		{
			System.out.println(" i am here");
			Details.append("| | Coming soon | |");
		}
		String movietitle="Movie Title   :   "+titlebar.select("h1").text();
		Details.append(movietitle+"\n\n");
		System.out.println("Title bar :"+movietitle);
		String movieduraction=titlebar.select("time").text();
		Details.append(movieduraction+"\n\n");
		System.out.println("Movie Duraction time :"+titlebar.select("time").text());
		String rating="IMDb Rating   :   "+titlebar.select(".ratingValue").text();
	
		System.out.println("IMDb Rating   :   "+rating);
		if(rating.length()>"IMDb Rating   :   ".length())
		{
		Details.append(rating+"\n\n");
		}
		
		
		Details.append("Story   :   ");
		Elements details=doc.select("div.plot_summary ");
		Details.append(details.select(".summary_text").text()+"\n\n");
		System.out.println();
		int k=1;
		for(Element element :details.select(".credit_summary_item"))
		{
			
			Details.append(element.text()+"\n\n");
			
		}
		Elements e=doc.select("div#titleDetails.article");
		for(Element element :e.select("div.txt-block") )
		{
			Details.append(element.text()+"\n\n");
			
		}
		
		return Details.toString();
	}
	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Movie name  :");
		String movie=sc.nextLine();
		String movielink=getMovielink(movie);
		if(!movielink.contains("null"))
		{
			details(movielink);
		
			String movieimagelink=getMoviePoster(movielink);
			movieimagelink=movieimagelink.substring(0,movieimagelink.indexOf("@"))+"@._V1_QL50_SY500_CR0,0,347,500_AL_.jpg";
			downloadimage(movieimagelink,movie,movielink);
		}
		else
		{
			System.out.println("No result found");
		}
		
	}
	
	

}
