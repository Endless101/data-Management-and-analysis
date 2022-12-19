/* match(n:proceeding)-[:APPEARED_IN]->(:conference {name: ' PODS '}) */
/* return n.publisher limit 1 ; */




/* match(auth:author {name: "Martin Grohe"})-[:AUTHORED]->(a:article)-[:APPEARED_IN]->(j:journal {name: " Theor. Comput. Sci. "}) 
return a.title; */



/*match(:inproceeding {year: "2022"})-[r:APPEARED_IN]->(:conference {name: ' SIGMOD Conference '})
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



/*match(n:editor)-[e:EDITED]->(:proceeding)-[*]->(:conference {name: ' PODS '})
with n, count(e) as amountEdited order by amountEdited desc limit 1
match(a:editor)-[v:EDITED]->(:proceeding)-[*]->(:conference {name: ' PODS '})
with a as a, count(v) as amount, max(amountEdited) as maxx
where amount = maxx
return a, amount;*/


/*match(n:author)-[r]->(pub)
where pub:article or pub:inproceeding
with n,count(r) as publications order by publications desc limit 1
match(n:author)--()-[rs]->(c:conference)
return n, count(distinct c);*/


match(c:conference)
where c.name starts with " ICTD "
return c;


