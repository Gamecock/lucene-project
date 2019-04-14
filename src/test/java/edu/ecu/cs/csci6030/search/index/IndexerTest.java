package edu.ecu.cs.csci6030.search.index;

import org.apache.lucene.document.Document;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.apache.lucene.index.IndexWriter;

import java.io.IOException;

import static org.easymock.EasyMock.*;

@RunWith(EasyMockRunner.class)
public class IndexerTest {

    @Mock
    IndexWriter writer;

    @Mock
    BibParser bibParser;

    Document nullDoc = null;

    @TestSubject
    Indexer indexer = new Indexer(writer, bibParser);

    @Test
    public void emptyTest() throws IOException {
        expect(bibParser.getNextBibEntry()).andReturn(nullDoc);
        writer.close();
        replay(writer, bibParser);
        indexer.index();
        verify(writer, bibParser);
    }

    @Test
    public void oneTest() throws IOException {
        Document doc1 = new Document();
        expect(bibParser.getNextBibEntry()).andReturn(doc1);
        expect(bibParser.getNextBibEntry()).andReturn(nullDoc);
        expect(writer.addDocument(doc1)).andReturn(1L);
        writer.close();
        replay(writer, bibParser);
        indexer.index();
        verify(writer, bibParser);
    }

    @Test
    public void manyTest() throws IOException {
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        expect(bibParser.getNextBibEntry()).andReturn(doc1);
        expect(bibParser.getNextBibEntry()).andReturn(doc2);
        expect(bibParser.getNextBibEntry()).andReturn(doc3);
        expect(bibParser.getNextBibEntry()).andReturn(nullDoc);
        expect(writer.addDocument(doc1)).andReturn(1L);
        expect(writer.addDocument(doc2)).andReturn(1L);
        expect(writer.addDocument(doc3)).andReturn(1L);
        writer.close();
        replay(writer, bibParser);
        indexer.index();
        verify(writer, bibParser);

    }
}