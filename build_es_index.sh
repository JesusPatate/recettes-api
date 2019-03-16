#!/usr/bin/env bash

curl -X PUT "localhost:9200/recipes"  -H 'Content-Type: application/json' -d '
{
    "mappings": {
      "recipe": {
        "properties": {
          "cookingTime": {
            "type": "short"
          },
          "dessert": {
            "type": "boolean"
          },
          "hot": {
            "type": "boolean"
          },
          "ingredients": {
            "type": "nested",
            "properties": {
              "amount": {
                "type": "integer",
                "index": false
              },
              "name": {
                "type": "text",
                "copy_to": [
                  "title_ingredients"
                ],
                "analyzer": "french"
              },
              "unitId": {
                "type": "integer",
                "index": false
              }
            }
          },
          "preparationTime": {
            "type": "short"
          },
          "servings": {
            "type": "byte"
          },
          "source": {
            "type": "text",
            "index": false
          },
          "title": {
            "type": "text",
            "copy_to": [
              "title_ingredients"
            ],
            "analyzer": "french"
          },
          "title_ingredients": {
            "type": "text",
            "analyzer": "french"
          }
        }
      }
    }
}
'
