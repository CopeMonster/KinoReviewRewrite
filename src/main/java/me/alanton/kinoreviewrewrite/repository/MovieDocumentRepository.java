package me.alanton.kinoreviewrewrite.repository;

import me.alanton.kinoreviewrewrite.document.MovieDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieDocumentRepository extends ElasticsearchRepository<MovieDocument, String> {
    @Query("""
            {
              "bool": {
                "should": [
                  {
                    "multi_match": {
                      "query": "?0",
                      "fields": ["name^4", "description^3", "country^2", "director^2", "year_of_release^1"],
                      "type": "best_fields",
                      "operator": "or"
                    }
                  },
                  {
                    "match_phrase": {
                      "name": {
                        "query": "?0",
                        "boost": 3
                      }
                    }
                  },
                  {
                    "match_phrase": {
                      "description": {
                        "query": "?0",
                        "boost": 2
                      }
                    }
                  },
                  {
                    "match_phrase": {
                      "country": {
                        "query": "?0",
                        "boost": 1.5
                      }
                    }
                  },
                  {
                    "match_phrase": {
                      "director": {
                        "query": "?0",
                        "boost": 1.5
                      }
                    }
                  },
                  {
                    "match_phrase": {
                      "year_of_release": {
                        "query": "?0",
                        "boost": 1.5
                      }
                    }
                  },
                  {
                    "terms": {
                      "genres": ["?0"]
                    }
                  }
                ],
                "minimum_should_match": 1
              }
            }
            """)
    Page<MovieDocument> searchByQuery(String query, Pageable pageable);
}
