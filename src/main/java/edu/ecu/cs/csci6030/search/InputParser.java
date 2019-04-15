package edu.ecu.cs.csci6030.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.search.Query;

import java.util.regex.Matcher;

public class InputParser {

    Analyzer analyzer;
    String[] terms;

    public InputParser(String[] terms, Analyzer analyzer) {
        this.analyzer = analyzer;
        this.terms = terms;
    }

    public Query parse(String input) throws ParseFailureException {
        Integer separation = null;
        String term2=null;
        Query query = null;

        String[] terms = input.split(" ");
        if ( terms.length > 3) throw new ParseFailureException("Too many terms in query");
        for (int i = 0; i < terms.length; i++ ) {
                terms[i] = terms[i].toLowerCase();
        }

        try {

            String term1 = terms[0];
            switch (terms.length) {
                case 3:
                    separation = parseSeparation(terms[1]);
                    term2 = terms[2];
                    break;
                case 2:
                    term2 = terms[1];
                    break;
                case 1:
                    query = getMultiFieldQuery(term1);
            }
        } catch ( Exception e) {
            throw new ParseFailureException(e.getMessage());
        }

        return query;
    }

    private Query getMultiFieldQuery(String term1) throws ParseException {
        MultiFieldQueryParser mfqp = new MultiFieldQueryParser(terms, analyzer);
        return mfqp.parse(term1);
    }

    private Integer parseSeparation(String term) {
//        Matcher matcher = numberPattern.matcher(term);
//        String sepString;
//        if (matcher.find()) {
//            sepString = matcher.group();
//        } else {
//            throw new ParseFailureException("No separation term.");
//        }
//        return Integer.parseInt(sepString);
        return 0;
    }

}
