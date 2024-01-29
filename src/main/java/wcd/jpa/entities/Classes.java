package wcd.jpa.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "classes")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "sem", nullable = false)
    private String sem;
    @Column(name = "room", nullable = false)
    private String room;

    @OneToMany(mappedBy = "classes")
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public Classes setStudents(List<Student> students) {
        this.students = students;
        return this;
    }

    public int getId() {
        return id;
    }

    public Classes setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Classes setName(String name) {
        this.name = name;
        return this;
    }

    public String getSem() {
        return sem;
    }

    public Classes setSem(String sem) {
        this.sem = sem;
        return this;
    }

    public String getRoom() {
        return room;
    }

    public Classes setRoom(String room) {
        this.room = room;
        return this;
    }
}
