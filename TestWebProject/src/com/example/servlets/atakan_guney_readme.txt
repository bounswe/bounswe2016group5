Atakan Guney's 6th cmpe.352 assignment Read.me file

My SPARQL QUERIES:

PREFIX wd: <http://www.wikidata.org/entity/>
PREFIX wdt: <http://www.wikidata.org/prop/direct/>
PREFIX wikibase: <http://wikiba.se/ontology#>
PREFIX p: <http://www.wikidata.org/prop/>
PREFIX ps: <http://www.wikidata.org/prop/statement/>
PREFIX pq: <http://www.wikidata.org/prop/qualifier/>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bd: <http://www.bigdata.com/rdf#>
#Most eponymous mathematicians
SELECT ?eponym ?eponymLabel ?count ?sample ?sampleLabel ?birthplace ?birthplaceLabel ?area ?areaLabel
WHERE
{
{
SELECT ?eponym (COUNT(?item) as ?count) (SAMPLE(?item) AS ?sample)
WHERE
{
?item wdt:P138 ?eponym.
?eponym wdt:P106 wd:Q170790.
}
GROUP BY ?eponym
}
?eponym wdt:P106 wd:Q170790 .
 ?eponym wdt:P101 ?area.
?eponym wdt:P19 ?place .
?place wdt:P625 ?coord .
?place wdt:P17 ?birthplace.
SERVICE wikibase:label { bd:serviceParam wikibase:language "en" }
}
ORDER BY DESC(?count)


What do these queries ? : 

My topic is about most eponym mathematicians. These queries give me 5 fields of a mathematician. 1. name, 2. sample of his/her work,3. number of work, 4. birth place and 5. worked/working area of the mathematician.

My data set : wikidata.

Examples:
data form: personal page | name | number of works | sample of wokrs | sample page | birthplace page | birthplace | area page | area

for Gauss:  wd:Q6722 | Carl Friedrich Gauss | 79 | wd:Q2658 | Gaussian | elimination | page of Germany| Germany |page of Number Theory |Number Theory 







