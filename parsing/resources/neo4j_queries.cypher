/* match(n:proceeding)-[:APPEARED_IN]->(:conference {name: ' PODS '}) */
/* return n.publisher limit 1 ; */




/* match(auth:author {name: "Martin Grohe"})-[:AUTHORED]->(a:article)-[:APPEARED_IN]->(j:journal {name: " Theor. Comput. Sci. "}) 
return a.title; */



/*match(:inproceeding)-[r:APPEARED_IN]->(:conference {name: ' SIGMOD Conference '})
return count(r);*/


/*match(n:article) with min(n.year) as v
match(s:article {year: v})-[:APPEARED_IN]->(j)
return j.name limit 1;*/



/*match(n:inproceeding)-[APPEARED_IN]->(:conference {name: ' CIDR '}) with n.year AS year, count(n) AS articlesPerYear
return apoc.agg.median(articlesPerYear);*/


/*match(:author)-[r:AUTHORED]->(n:inproceeding)-[:APPEARED_IN]->(:conference {name: ' SIGMOD Conference '})
with count(r) as amountOfAuthors, n as inprocs
where amountOfAuthors > 10
with count(inprocs) as amountOfArticles, inprocs.year as v
return v order by amountOfArticles desc limit 1;*/



match(n:editor)-[e:EDITED]->(:proceeding)-[*]->(:conference {name: ' PODS '})
with n, count(e) as amountEdited
with *, collect(amountEdited) as mef
unwind mef as he
return he;






