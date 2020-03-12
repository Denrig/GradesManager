package Utils;

import Services.StudentService;
import Services.TemaGradeService;
import Services.TemeService;

public class Linker {
    private static Linker instance = null;
    private TemeService temeService;
    private StudentService studentService;
    private TemaGradeService gradeService;


    private Linker() {
        temeService = new TemeService(1l);
        studentService = new StudentService(1l);
        gradeService = new TemaGradeService(1l);
    }

    public static Linker getInstance() {
        if (instance == null)
            instance = new Linker();

        return instance;
    }

    public TemeService getTemeService() {
        if (instance != null)
            return temeService;
        return null;
    }

    public StudentService getStudentService() {
        if (instance != null)
            return studentService;
        return null;
    }

    public TemaGradeService getGradeService() {
        if (instance != null)
            return gradeService;
        return null;
    }
}
