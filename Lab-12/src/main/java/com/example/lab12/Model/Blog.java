package com.example.lab12.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Column(columnDefinition = "varchar(40) not null")
    private String title;
    @NotEmpty
    @Column(columnDefinition = "varchar(4000) not null")
    private String body;
    @ManyToOne
    @JsonIgnore
    private User user;
}
