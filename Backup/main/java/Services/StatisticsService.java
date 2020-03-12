package Services;

import Entity.Assignment;
import Entity.Discipline;
import Entity.Student;
import Utils.Linker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Period;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

public class StatisticsService {
    private Linker linker = Linker.getInstance();

    public Double RaportNota(Student student) {
        AtomicInteger pondere = new AtomicInteger(0);
        AtomicReference<Double> media = new AtomicReference<>((double) 0);
        StreamSupport.stream(linker.getGradeService().findAll().spliterator(), false).filter(s -> s.getStudentID().equals(student.getId())).forEach(nota -> {
            Period period = Period.between(linker.getTemeService().findOne(nota.getTemaId()).getEndWeek(), linker.getTemeService().findOne(nota.getTemaId()).getStartWeek());
            pondere.set(pondere.get() + period.getDays());
            media.set(media.get() + nota.getNota() * period.getDays());
        });

        return media.get() / pondere.get();
    }

    public Assignment TemaDificila() {
        AtomicReference<Assignment> tema1 = new AtomicReference<Assignment>();
        AtomicReference<Double> min = new AtomicReference<Double>(10D);
        AtomicReference<Double> media = new AtomicReference<>((double) 0);
        AtomicInteger counter = new AtomicInteger(0);
        StreamSupport.stream(linker.getTemeService().findAll().spliterator(), false).forEach(tema -> {
            media.set(0D);
            counter.set(0);

            StreamSupport.stream(linker.getGradeService().findAll().spliterator(), false).filter(grade -> grade.getTemaId().equals(tema.getId())).forEach(grade -> {
                media.set(media.get() + grade.getNota());
                counter.set(counter.get() + 1);
            });

            media.set(media.get() / counter.get());

            if (min.get() > media.get()) {
                tema1.set(tema);
                min.set(media.get());
            }


        });
        return tema1.get();
    }

    public boolean studentiPunctuali(Student student) {
        AtomicBoolean add = new AtomicBoolean(true);

        StreamSupport.stream(linker.getGradeService().findAll().spliterator(), false).filter(grade -> grade.getStudentID().equals(student.getId())).forEach(grade -> {
            if (grade.getPredataIn().isAfter(linker.getTemeService().findOne(grade.getTemaId()).getEndWeek()))
                add.set(false);
        });
        return add.get();
    }

    public Iterable<Student> getStudents() {
        return linker.getStudentService().findAll();
    }

    public void getCSV(Discipline discipline) throws IOException {
        File csv = new File("Resources\\Academic.csv");
        GradeService service = new GradeService(discipline.getId());
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csv));

        getStudents().forEach(student -> {
            try {
                bufferedWriter.write(student.getNume() + "," + discipline.getNume() + "," + service.getFinalGrade(student, discipline.getId()) + "\n");
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });

        bufferedWriter.close();
    }
}
