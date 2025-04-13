package nikita.verhovod.profi.repositories;

import java.util.List;
import nikita.verhovod.profi.data.students.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student s WHERE " +
        "LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
        "LOWER(s.group.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Student> searchStudents(@Param("query") String query);
}
