import com.dfsek.protolith.optics.interact.Set;
import com.dfsek.protolith.optics.interact.View;
import com.dfsek.protolith.optics.lens.SimpleLens;
import dummy.Classroom;
import dummy.Person;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LensTest {
    @Test
    public void lensView() {
        Person gerald = new Person("Gerald", 20);

        int geraldAge = View.view(Person.AGE, gerald);

        assertEquals(20, geraldAge);
    }

    @Test
    public void lensSet() {
        Person gerald = new Person("Gerald", 20);

        Person olderGerald = Set.set(Person.AGE, 21, gerald);

        assertEquals(21, olderGerald.getAge());
    }

    @Test
    public void compositionBasic() {
        Classroom classroom = new Classroom(List.of(), new Person("Penny", 25));

        SimpleLens<Classroom, Integer> teacherAge = SimpleLens.adapt(Person.AGE.compose(Classroom.TEACHER));

        assertEquals(25, View.view(teacherAge, classroom));
    }
}
