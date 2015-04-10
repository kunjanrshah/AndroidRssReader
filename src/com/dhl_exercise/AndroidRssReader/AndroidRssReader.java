package com.dhl_exercise.AndroidRssReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AndroidRssReader extends ListActivity {
	
	private RSSFeed myRssFeed = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        
        try {
			URL rssUrl = new URL("http://www.gov.hk/en/about/rss/govhkrss.data.xml");
			SAXParserFactory mySAXParserFactory = SAXParserFactory.newInstance();
			SAXParser mySAXParser = mySAXParserFactory.newSAXParser();
			XMLReader myXMLReader = mySAXParser.getXMLReader();
			RSSHandler myRSSHandler = new RSSHandler();
			myXMLReader.setContentHandler(myRSSHandler);
			InputSource myInputSource = new InputSource(rssUrl.openStream());
			myXMLReader.parse(myInputSource);
			
			myRssFeed = myRSSHandler.getFeed();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (myRssFeed!=null)
		{
			TextView feedTitle = (TextView)findViewById(R.id.feedtitle);
			TextView feedDescribtion = (TextView)findViewById(R.id.feeddescribtion);
			TextView feedPubdate = (TextView)findViewById(R.id.feedpubdate);
			TextView feedLink = (TextView)findViewById(R.id.feedlink);
			feedTitle.setText(myRssFeed.getTitle());
			feedDescribtion.setText(myRssFeed.getDescription());
			feedPubdate.setText(myRssFeed.getPubdate());
			feedLink.setText(myRssFeed.getLink());
			
			ArrayAdapter<RSSItem> adapter = 
				new	ArrayAdapter<RSSItem>(this,
						android.R.layout.simple_list_item_1,myRssFeed.getList());
			setListAdapter(adapter);
		}
    }
}