package edu.ecu.cs.csci6030.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputParserTest {

    String[] terms = {"money", "class"};
    Analyzer analyzer = new StandardAnalyzer();


    Query query;
    private InputParser parser;

    @Before
    public void setUp() {
        parser = new InputParser(terms, analyzer);
    }

    @Test
    public void simpleQueryTest() {
        query = parser.parse("United");
        assertEquals(BooleanQuery.class, query.getClass());
    }

    @Test
    public void  termQueryTest() {
        query = parser.parse("class:test");
        assertEquals(TermQuery.class, query.getClass());
    }


}