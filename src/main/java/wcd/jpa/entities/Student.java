package wcd.jpa.entities;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// khoa chinh tu dong tang
    public int id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = true)
    public String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    // FetchType.LAZY : đến đâu lấy đến đấy
    // FetchType.EAGER : lấy hết
    private Classes classes;

    public Classes getClasses() {
        return classes;
    }

    public Student setClasses(Classes classes) {
        this.classes = classes;
        return this;
    }

    public int getId() {
        return id;
    }

    public Student setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Student setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Student setAddress(String address) {
        this.address = address;
        return this;
    }
}
