package mk.ukim.finki.wp.lab.service.Impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exeption.StudentNotFound;
import mk.ukim.finki.wp.lab.model.exeption.StudentsByNameOrSurnameNotFound;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Student> listAll() {
        return repository.findAll();
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        if(text==null || text.isEmpty()){
            throw new IllegalArgumentException("Text not valid");
        }

        List<Student> students = repository.findAllByNameOrSurname(text,text);

        if(students == null || students.isEmpty()) {
            throw new StudentsByNameOrSurnameNotFound(text);
        }
        return students;
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty()
        || name==null || name.isEmpty() || surname==null || surname.isEmpty())
        {
            throw new IllegalArgumentException("Arguments not vaild");
        }
        return repository.save(new Student(username, password, name, surname));
    }
}
