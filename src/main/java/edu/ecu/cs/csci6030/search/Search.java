package edu.ecu.cs.csci6030.search;

import edu.ecu.cs.csci6030.search.index.BibParser;
import edu.ecu.cs.csci6030.search.index.Indexer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.File;
import java.nio.file.Paths;

public class Search {

    static final int MAX_DOCS = 10;
    static final String[] terms = {"title", "abstract", "year"};

     static void usage() {
        System.out.println("Usage: java " + Search.class.getName()
                + " [pathToDirectory [maxFiles]]");
     }

    public static void main(String[] args) {
        String prompt = "search> ";
        String rightPrompt = null;
        Query query = null;

        // default index path
        String indexPath = "target\\";
        String bibFile = "cs-bibliography2.bib";

        // create a standard analyzer
        Analyzer analyzer = new StandardAnalyzer();

        // set IndexWriter configuration
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        // Create a new index in the directory, removing any
        // previously indexed documents:
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        String line = null;
        try {

            // directory for writing Lucene index
            Directory dir = FSDirectory.open(Paths.get(indexPath));

            IndexWriter writer = new IndexWriter(dir, iwc);

//            Create a BibParser and Index to process the bib
            BibParser bibParser = new BibParser(new File(bibFile));
            Indexer indexer = new Indexer(writer, bibParser);

            indexer.index();

            //Index Created, now search

            TerminalBuilder builder = TerminalBuilder.builder();

            Terminal terminal = builder.build();

            LineReaderImpl reader = (LineReaderImpl) LineReaderBuilder.builder()
                    .terminal(terminal)
                    .variable(LineReader.SECONDARY_PROMPT_PATTERN, "%M%P > ")
                    .build();

            InputParser inputParser = new InputParser(terms, analyzer);
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
            IndexSearcher searcher = new IndexSearcher(indexReader);

            while (true) {

                try {
                    line = reader.readLine(prompt, rightPrompt,  null, null);
                } catch (UserInterruptException e) {
                    // Ignore
                } catch (EndOfFileException e) {
                    return;
                }
                if (line == null) {
                    continue;
                }

                line = line.trim();

                terminal.flush();
                final long startTime = System.currentTimeMillis();

                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                else if (line.equalsIgnoreCase("sleep")) {
                    Thread.sleep(3000);
                    break;
                } else {
                    query = getQuery(inputParser, line);
                    TopDocs topDocs = searcher.search(query, MAX_DOCS);
                    System.out.println("\nTotal matches: " + topDocs.totalHits);
                    ScoreDoc[] resultSet = topDocs.scoreDocs;
                    final long stopTime = System.currentTimeMillis();
                    printResults(resultSet);
                    System.out.println ("Returned "+resultSet.length+ " documents in "+ (stopTime-startTime) + " milli-seconds.");
                }


            }

        } catch (Exception e) {
            System.out.println("Exception");
            System.out.println(e.getMessage());
        }
        System.out.println("Done");


    }


    static Query getQuery(InputParser inputParser, String line) {
        Query query = null;
        try {
            query = inputParser.parse(line);
//            results = query.search(index);
        } catch (ParseFailureException pfe) {
            System.out.println(pfe.getMessage());
            printQueryInstructions();
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
        }
        return query;
    }


    private static void printQueryInstructions() {
         System.out.println("Query Failed, query should be one of these \n1. Two terms 'field' 'word'");
//         System.out.println(" Two term boolean AND 'one word'\n3. Proximity query 'near /4 annother'");
    }

    static void printResults(ScoreDoc[] results) {
         if (null == results) return;
         if (0 == results.length) {
             System.out.println("Nothing Found");
         } else {
             for (int i = 0; i < results.length; i++) {
                 System.out.println("Document = " + results[i].doc + "\t" + " Score=" + results[i].score);
             }
         }
    }

}
