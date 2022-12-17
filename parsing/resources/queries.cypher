 CREATE INDEX article IF NOT EXISTS for (a:article) on (a.key);
 CREATE INDEX proceeding IF NOT EXISTS  for (p:proceeding) on (p.key);
 CREATE INDEX inproceeding IF NOT EXISTS   for (ip:inproceeding) on (ip.key);
 CREATE INDEX author IF NOT EXISTS  for (a:author) on (a.key);
 CREATE INDEX conference IF NOT EXISTS  for (c:conference) on (c.key);
 CREATE INDEX journal IF NOT EXISTS  for (j:journal) on (j.key);
 CREATE INDEX editor IF NOT EXISTS  for (e:editor) on (e.key);
 CALL db.awaitIndexes();



 LOAD CSV WITH HEADERS FROM 'file:///article.csv' as row
 CALL {
 with row
     CREATE(a:article {
     volume: row.volume,
     number: row.number,
     pages: row.pages,
     journal: row.journal,
     year: row.year,
     publisher: row.publisher,
     title: row.title,
     key: row.key
             })
 } IN TRANSACTIONS OF 100 ROWS;


LOAD CSV WITH HEADERS FROM 'file:///proceedings.csv' as row
CALL {
with row
    CREATE(p:proceeding {
    volume: row.volume,
    year: row.year,
    publisher: row.publisher,
    booktitle: row.booktitle,
    title: row.title,
    key: row.key
            })
} IN TRANSACTIONS OF 100 ROWS;


LOAD CSV WITH HEADERS FROM 'file:///inproceeding.csv' as row
CALL {
with row
    CREATE(ip:inproceeding {
    year: row.year,
    pages: row.pages,
    booktitle: row.booktitle,
    title: row.title,
    key: row.key
            })
} IN TRANSACTIONS OF 100 ROWS;



 LOAD CSV WITH HEADERS FROM 'file:///journal.csv' as row
 CALL {
 with row
     CREATE(j:journal {
     journal: row.journal,
     key: row.key
             })
 } IN TRANSACTIONS OF 100 ROWS;


 LOAD CSV WITH HEADERS FROM 'file:///conference.csv' as row
 CALL {
 with row
     CREATE(c:conference {
     conference: row.conference,
     key: row.key
             })
 } IN TRANSACTIONS OF 100 ROWS;



 LOAD CSV WITH HEADERS FROM 'file:///editor.csv' as row
 CALL {
 with row
     CREATE(e:editor {
     editor: row.editor,
     key: row.key
             })
 } IN TRANSACTIONS OF 100 ROWS;


 LOAD CSV WITH HEADERS FROM 'file:///author.csv' as row
 CALL {
 with row
     CREATE(j:author {
     author: row.author,
     key: row.key
             })
 } IN TRANSACTIONS OF 100 ROWS;

 CALL apoc.periodic.iterate('MATCH(ar:article) 
                             MATCH(a:auhtor {key: ar.key}) 
                             RETURN a,ar',' 
                             CREATE(a)-[:AUTHORED]->(ar)', 
                             {batchsize: 1000});

 CALL apoc.periodic.iterate('MATCH(ar:article) 
                             MATCH(j:journal {key: ar.key}) 
                             RETURN ar,j',' 
                             CREATE(ar)-[:PUBLISHED_IN]->(j)', 
                             {batchsize: 1000});


 CALL apoc.periodic.iterate('MATCH(ip:inproceeding) 
                             MATCH(a:auhtor {key: ip.key}) 
                             RETURN a,ip','
                             CREATE(a)-[:AUTHORED]->(ip)', 
                             {batchsize: 1000});



 CALL apoc.periodic.iterate('MATCH(ip:inproceeding) 
                             MATCH(c:conference {key: ip.key}) 
                             RETURN c,ip',' 
                             CREATE(ip)-[:APPEARED_AT]->(c)', 
                             {batchsize: 1000});


 CALL apoc.periodic.iterate('MATCH(p:proceeding) 
                             MATCH(c:conference {key: p.key}) 
                             RETURN c,p',' 
                             CREATE(p)-[:PROCEEDING_OF]->(c)', 
                             {batchsize: 1000});





 CALL apoc.periodic.iterate('MATCH(p:proceeding) 
                             MATCH(e:editor {key: p.key}) 
                             RETURN e,p',' 
                             CREATE(e)-[:EDITED]->(p)', 
                             {batchsize: 1000});





