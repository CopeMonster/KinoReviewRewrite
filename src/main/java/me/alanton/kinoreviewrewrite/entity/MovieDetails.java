package me.alanton.kinoreviewrewrite.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "movie_details")
public class MovieDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Lob
    @Column(name = "description")
    String description;

    @Column(name = "year_of_release", nullable = false)
    String yearOfRelease;

    @Column(name = "country", nullable = false)
    String country;

    @Column(name = "director", nullable = false)
    String director;

    @Column(name = "duration", nullable = false)
    Integer duration;

    @Column(name = "rating", nullable = false)
    Float rating;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    Movie movie;
}
