# search
Search Homework CSCI6030 Programming Assignment 3

This program can be built with maven to generate a jar. 
Hard coded to work with cs-bibliography2.bib.
I changed the line endings on the file to work with windows, changing op systems kicked my butt. 

To run `java -jar target/lucene-1.0-SNAPSHOT.jar <path to directory > [maxFiles]`.

Then you can search that directory for:  
1. single term `word` in any field
1. single term in a field `field:word`

To exit the application type `quit` or `exit`.
