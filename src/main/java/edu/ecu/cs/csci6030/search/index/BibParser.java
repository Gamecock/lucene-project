package edu.ecu.cs.csci6030.search.index;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import java.io.*;

public class BibParser {

    BufferedReader br;

    public BibParser(File file){
        try {
            br = new BufferedReader(new FileReader(file));
        } catch ( FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
    }

    public Document getNextBibEntry(){
        Document doc = null;
        String line;
        try {
            while(null != (line = br.readLine())){
                line = line.trim();
                if (line.equals("")) continue;
                if ('@' == line.charAt(0)) {
                    doc = extractBibEntry(line);
                }
                if (null != doc) break;
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return doc;
    }

    private Document extractBibEntry(String line) throws IOException {
        Document bibEntry = new Document();
        while (null != (line = br.readLine())) {
            line = line.trim();
            String key = extractKey(line);
            String value = extractValue(line);

            if("abstract".equals(key)){
                bibEntry.add(new TextField(key, value, Field.Store.YES));
            }
            if("title".equals(key)){
                bibEntry.add(new TextField(key, value, Field.Store.YES));
            }
            if("year".equals(key)){
                bibEntry.add(new StringField(key, value, Field.Store.YES));
            }

            if (line.equals("}")) {
                break;
            }
        }
        return bibEntry;
    }

    private String extractValue(String line) {
        String value = null;
        int begin = line.indexOf("{")+1;
        int end = line.indexOf("}");
        if (begin >0 & end > 0){
            value = line.substring(begin,end);
        } else {
            int split = line.indexOf("=");
            value = line.substring(split+1).trim();
        }
        return value;
    }

    private String extractKey(String line) {
        String key = null;
        int split = line.indexOf('=');
        if ( split > 0) {
            key = line.substring(0, split).trim();
        }
        return key;
    }

}
