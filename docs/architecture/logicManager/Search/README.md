
# Overview

This section introduces the "Search" layer in the "platform" project, which contains the Java classes that provide the search functions of our product.

## BsearchService

`BsearchService` is the central service class for searching. This class executes searches by generating SQL commands based on the provided search context and search node configurations. 
For more detailed information, please refer to:
- [BSearchService](BSearchService.md)


## SearchContext
A Search Context Model class for passing the context information when executing a search. This context information may include parameters such as user login, client, search headers.....


## BSearchResponse

A Search Response Model class for returning the search results. It contains a list of search results and the total number of records.
as well as A list of UUIDs corresponding to the search results, if required (uuidList).