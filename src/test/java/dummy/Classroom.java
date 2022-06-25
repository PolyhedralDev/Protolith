package dummy;

import com.dfsek.protolith.optics.lens.SimpleLens;
import io.vavr.collection.List;

public class Classroom {
    public static SimpleLens<Classroom, Person> TEACHER = SimpleLens.lens(Classroom::getTeacher, Classroom::withTeacher);
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
}
