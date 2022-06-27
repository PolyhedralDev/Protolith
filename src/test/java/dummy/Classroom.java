package dummy;

import com.dfsek.protolith.optics.SimpleLens;
import io.vavr.collection.List;

public class Classroom {
    public static SimpleLens<Classroom, Person> TEACHER = SimpleLens.of(Classroom::getTeacher, Classroom::withTeacher);
    public static SimpleLens<Classroom, List<Person>> STUDENTS = SimpleLens.of(Classroom::getStudents, Classroom::withStudents);
    private final List<Person> students;
    private final Person teacher;

    public Classroom(List<Person> students, Person teacher) {
        this.students = students;
        this.teacher = teacher;
    }

    public Classroom addStudent(Person student) {
        return new Classroom(students.append(student), teacher);
    }

    public List<Person> getStudents() {
        return students;
    }

    public Person getTeacher() {
        return teacher;
    }

    public Classroom withTeacher(Person person) {
        return new Classroom(students, person);
    }

    public Classroom withStudents(List<Person> students) {
        return new Classroom(students, this.teacher);
    }
}
