package wcd.jpa.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int hours;

    @ManyToMany(mappedBy = "subjects")
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public Subject setStudents(List<Student> students) {
        this.students = students;
        return this;
    }

    public int getId() {
        return id;
    }

    public Subject setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subject setName(String name) {
        this.name = name;
        return this;
    }

    public int getHours() {
        return hours;
    }

    public Subject setHours(int hours) {
        this.hours = hours;
        return this;
    }
}
