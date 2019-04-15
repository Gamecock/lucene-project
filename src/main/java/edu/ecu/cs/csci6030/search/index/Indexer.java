package edu.ecu.cs.csci6030.search.index;


import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

public class Indexer {

    private BibParser bibParser;
    private IndexWriter writer;

    public Indexer(IndexWriter writer, BibParser bibparser) {
        this.bibParser = bibparser;
        this.writer = writer;
    }

    public void index() {
        Document doc = bibParser.getNextBibEntry();
        while (null != doc) {
            try {
                writer.addDocument(doc);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            doc = bibParser.getNextBibEntry();
        }

        try {
            writer.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }


}
