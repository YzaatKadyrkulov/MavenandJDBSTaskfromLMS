package org.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Job {
    private Long id;
    private String position; //("Mentor","Management","Instructor")
    private String profession;   //("Java","JavaScript")
    private String description;   //("Backend developer","Fronted developer")
    private int experience;//(1,2,3);

    public Job(String position, String profession, String description, int experience) {
        this.id = id;
        this.position = position;
        this.profession = profession;
        this.description = description;
        this.experience = experience;
    }
}
