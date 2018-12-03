package File_format;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import GIS.Element;

public class Csv2kml {

	/*
	 * Create a new myKml.kml file by reading a *.csv file from
	 * a pathname.
	 */
    public static void csvToKml(String pathname)  {

        //String csvFile = "C:/temp/WigleWifi_20171201110209.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String output = "myKml.kml";
        
        try {
        	
            br = new BufferedReader(new FileReader(pathname));
            br.readLine(); //read the first line, its not necessary
            br.readLine(); //read the second line, its not necessary
            File file = new File(output);
            if(!file.createNewFile()){ //if the file exists, delete it and start over.
            	file.delete();
            	writeToKmlStart(output);
            } else { //file created and start normaly.
            	writeToKmlStart(output);
            }
            while ((line = br.readLine()) != null) { //read parameters starting from 3rd line.
                // use comma as separator
                String[] string = line.split(cvsSplitBy);
                writeToKmlElement(string,output);
            }
            writeToKmlEnd(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    
    /*
     * helper method to build the first lines
     * of a kml file.
     */
    public static void writeToKmlStart(String path) throws IOException{
    	String web = "\"http://www.opengis.net/kml/2.2/\"";
        String kmlstart = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
        				  "<kml xmlns=" + web + 
        				  "><Document><Style id=" + "\"red\"" + "><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=" + 
        				  "\"yellow\"" + "><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=" + 
        				  "\"green\"" + "><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>\n";
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(kmlstart);
        writer.close();
    }
    
    /*
     * helper method to build an "element" (aka placemark) 
     * inside the kml file, using an array of strings
     */
    public static void writeToKmlElement(String[] line,String path) throws IOException{	
        String kmlelement = "<Placemark>\n" +
                			"<name>" + "<![CDATA[" + line[1] + "]]></name>\n" +
                			"<description>" + "<![CDATA[BSSID: <b>"  + line[0] + "</b><br/>Capabilities: <b>" + line[2] + "</b><br/>Channel: <b>" + line[4] + "</b><br/>Timestamp: <b>" + line[3] + "</b>]]></description><styleUrl>#red</styleUrl>\n" +
                			"<Point>\n" +
                			"<coordinates>" + line[7] + "," + line[6] + "</coordinates>\n" +
                			"</Point>\n" +
                			"</Placemark>\n";
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(path,true));
        writer.write(kmlelement);
        writer.close();
    }
    
    /*
     * helper method to insert the proper ending for
     * a kml file.
     */
    public static void writeToKmlEnd(String path) throws IOException{	
    	String kmlend = "</Folder>\n" + "</Document></kml>";
        BufferedWriter writer = new BufferedWriter(new FileWriter(path,true));
        writer.write(kmlend);
        writer.close();
    }

//    public static void main(String[] args) throws IOException{
//    	String path = "C:/temp/WigleWifi_20171203085618.csv";
//    	//String output = "C:/temp/test.kml";
//    	csvToKml(path);
//    }
}