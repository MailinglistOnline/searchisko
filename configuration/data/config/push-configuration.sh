curl -X POST -d @search_fulltext_query_fields.json -u mlonline:admin http://searchisko-mlonline.rhcloud.com/v1/rest/config/search_fulltext_query_fields --header "Content-Type:application/json"

curl -X POST -d @search_fulltext_highlight_fields.json -u mlonline:admin http://searchisko-mlonline.rhcloud.com/v1/rest/config/search_fulltext_highlight_fields --header "Content-Type:application/json"

curl -X POST -d @search_response_fields.json -u mlonline:admin http://searchisko-mlonline.rhcloud.com/v1/rest/config/search_response_fields --header "Content-Type:application/json" 

