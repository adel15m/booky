package com.example.booky.openlibrary;

import com.example.booky.openlibrary.dto.OLBookDto;
import com.example.booky.openlibrary.exception.OLBookNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenLibraryService {

    private final RestTemplate restTemplate = new RestTemplate();
    public OLBookDto getBook(String isbn) throws OLBookNotFoundException {

        // Build the API URL
        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";

        // Fetch data from the API
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // Parse the JSON response
            try {
                JsonNode rootNode = new ObjectMapper().readTree(response.getBody());
                JsonNode bookNode = rootNode.path("ISBN:" + isbn);

                if (!bookNode.isMissingNode()) {
                    String title = bookNode.path("title").asText();
                    int pages = bookNode.path("number_of_pages").asInt();
                    String author = getAuthors(bookNode);

                    return OLBookDto.builder()
                            .isbn(isbn)
                            .title(title)
                            .author(author)
                            .pages(pages)
                            .build();
                }
            } catch (JsonProcessingException e) {
                throw new OLBookNotFoundException();
            }
        }
        throw new OLBookNotFoundException();
    }

    private String getAuthors(JsonNode bookNode) {
        JsonNode authors = bookNode.path("authors");
        StringBuilder authorText = new StringBuilder(authors.get(0).path("name").asText());
        for (int i = 1; i < authors.size(); i++) {
            authorText.append(", ").append(authors.get(i).path("name").asText());
        }
        return authorText.toString();
    }







}
