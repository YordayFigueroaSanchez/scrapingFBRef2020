package scraping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class scrapingFBRef2020 {

	public static final String xmlFilePath = "xmlfile.xml";

	public static void main(String[] args) throws ParserConfigurationException, TransformerConfigurationException {
		// TODO Auto-generated method stub
		
		ArrayList<String> arrayUrlBoxScoreJornada01 = new ArrayList<String>(
				Arrays.asList(
						"https://fbref.com/en/matches/82b4ca95/Girona-Valladolid-August-17-2018-La-Liga",
						"https://fbref.com/en/matches/9fe3febe/Real-Betis-Levante-August-17-2018-La-Liga",
						"https://fbref.com/en/matches/a8d60ff6/Celta-Vigo-Espanyol-August-18-2018-La-Liga",
						"https://fbref.com/en/matches/aeeb77fc/Villarreal-Real-Sociedad-August-18-2018-La-Liga",
						"https://fbref.com/en/matches/e95b2588/Barcelona-Alaves-August-18-2018-La-Liga",
						"https://fbref.com/en/matches/446f0d99/Eibar-Huesca-August-19-2018-La-Liga",
						"https://fbref.com/en/matches/d1aa3271/Rayo-Vallecano-Sevilla-August-19-2018-La-Liga",
						"https://fbref.com/en/matches/25f5e47e/Real-Madrid-Getafe-August-19-2018-La-Liga",
						"https://fbref.com/en/matches/1202acb1/Valencia-Atletico-Madrid-August-20-2018-La-Liga",
						"https://fbref.com/en/matches/95ac7d44/Athletic-Bilbao-Leganes-August-20-2018-La-Liga")
				);
		ArrayList<String> arrayUrlBoxScoreJornada02 = new ArrayList<String>(
				Arrays.asList(
						"https://fbref.com/en/matches/aed2b90b/Getafe-Eibar-August-24-2018-La-Liga",
						"https://fbref.com/en/matches/75220ead/Leganes-Real-Sociedad-August-24-2018-La-Liga",
						"https://fbref.com/en/matches/31b10b4c/Alaves-Real-Betis-August-25-2018-La-Liga",
						"https://fbref.com/en/matches/9dafff66/Atletico-Madrid-Rayo-Vallecano-August-25-2018-La-Liga",
						"https://fbref.com/en/matches/262193b3/Valladolid-Barcelona-August-25-2018-La-Liga",
						"https://fbref.com/en/matches/9c6a2f94/Espanyol-Valencia-August-26-2018-La-Liga",
						"https://fbref.com/en/matches/ec7274c0/Sevilla-Villarreal-August-26-2018-La-Liga",
						"https://fbref.com/en/matches/5aa7f90b/Girona-Real-Madrid-August-26-2018-La-Liga",
						"https://fbref.com/en/matches/37c34100/Levante-Celta-Vigo-August-27-2018-La-Liga",
						"https://fbref.com/en/matches/0d234b90/Athletic-Bilbao-Huesca-August-27-2018-La-Liga")
				);
		ArrayList<String> arrayUrlBoxScoreJornada03 = new ArrayList<String>(
				Arrays.asList(
						"https://fbref.com/en/matches/3505425b/Getafe-Valladolid-August-31-2018-La-Liga",
						"https://fbref.com/en/matches/e9a96548/Eibar-Real-Sociedad-August-31-2018-La-Liga",
						"https://fbref.com/en/matches/d376ad57/Villarreal-Girona-August-31-2018-La-Liga",
						"https://fbref.com/en/matches/612c7914/Celta-Vigo-Atletico-Madrid-September-1-2018-La-Liga",
						"https://fbref.com/en/matches/273a53fc/Real-Madrid-Leganes-September-1-2018-La-Liga",
						"https://fbref.com/en/matches/8b8f19c0/Levante-Valencia-September-2-2018-La-Liga",
						"https://fbref.com/en/matches/efc2a670/Alaves-Espanyol-September-2-2018-La-Liga",
						"https://fbref.com/en/matches/0b6d40c5/Barcelona-Huesca-September-2-2018-La-Liga",
						"https://fbref.com/en/matches/386b9a82/Real-Betis-Sevilla-September-2-2018-La-Liga")
				);
		String urlBoxscores = "https://fbref.com/en/matches/0b6d40c5/Barcelona-Huesca-September-2-2018-La-Liga";
		String urlPbp = "https://www.basketball-reference.com/pbp/202001310BRK.html";
		Scrapeador scrap = new Scrapeador();
		
		scrap.toXML("01", urlBoxscores);
//		scrap.toXML("01", arrayUrlBoxScoreJornada01);
//		scrap.toXML("02", arrayUrlBoxScoreJornada02);
//		scrap.toXML("03", arrayUrlBoxScoreJornada03);

	}
}
