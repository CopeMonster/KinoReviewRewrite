package me.alanton.kinoreviewrewrite.document;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document(indexName = "movies")
public class MovieDocument {
    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long movieId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private List<String> genres;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text, name = "year_of_release")
    private String yearOfRelease;

    @Field(type = FieldType.Text)
    private String country;

    @Field(type = FieldType.Text)
    private String director;
}
