select count(distinct inproceeding_key)
from inproceeding
inner join conference
on inproceeding_key = conference.proceedings_key
where inproceeding.year = '2022' AND conference.title = ' SIGMOD Conference ';



select author,count(distinct conference.title)
from (select *
from author
where author.author =
      (select author
      from(select author,max(publishcount)
       from (select author, count(author) as publishcount
             from author
             group by author
             order by count(author.author) desc) as maxAuthorPublishCount) as maxAuthor)) as maxAuthorAndKeyTable
inner join conference
on maxAuthorAndKeyTable.publication_key = conference.proceedings_key;

