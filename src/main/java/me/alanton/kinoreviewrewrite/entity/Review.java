package me.alanton.kinoreviewrewrite.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "title", nullable = false)
    String title;

    @Lob
    @Column(name = "pros")
    String pros;

    @Lob
    @Column(name = "cons")
    String cons;

    @Lob
    @Column(name = "description")
    String description;

    @Column(name = "rating", nullable = false)
    float rating;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    Actor actor;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;
}
