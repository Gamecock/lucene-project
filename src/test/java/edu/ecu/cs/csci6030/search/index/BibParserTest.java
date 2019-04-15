package edu.ecu.cs.csci6030.search.index;

import org.apache.lucene.document.Document;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;


public class BibParserTest {


    BibParser bibParser;

    @Test
    public void  emptyStringNoResults(){
        bibParser = new BibParser(new File("src/test/resources/empty.bib"));
        assertEquals(null, bibParser.getNextBibEntry());
    }

    @Test
    public void  oneResults() {
        File file = new File("src/test/resources/one-file.bib");
        bibParser = new BibParser(file);
        Document bibEntry = bibParser.getNextBibEntry();
        assertNotNull( bibEntry);
        assertEquals("Title", "Computational Thinking in High School Courses", bibEntry.get("title"));
        Document bibEntry2 = bibParser.getNextBibEntry();
        assertNull( bibEntry2);
    }

    @Test
    public void  twoResults() {
        File file = new File("src/test/resources/two-file.bib");
        bibParser = new BibParser(file);
        Document bibEntry = bibParser.getNextBibEntry();
        assertNotNull( bibEntry);
        assertTrue(bibEntry.get("abstract").startsWith("The number of undergraduates"));
        assertEquals("Title", "Computational Thinking in High School Courses", bibEntry.get("title"));
        assertEquals("2010", bibEntry.get("year"));
        System.out.println("Second call");
        Document bibEntry2 = bibParser.getNextBibEntry();
        assertNotNull(bibEntry2);
        assertNull(bibEntry2.get("abstract"));
        assertEquals("Title", "The logic and limits of trust", bibEntry2.get("title"));
        assertEquals("1983", bibEntry2.get("year"));
    }

}