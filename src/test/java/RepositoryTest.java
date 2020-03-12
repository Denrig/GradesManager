import Entity.Grade;
import Repository.GradeRepository;
import Validator.GradeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class RepositoryTest {
    InputStream stream = RepositoryTest.class.getResourceAsStream("connUtils.properties");
    Properties properties=new Properties();
    private static final Logger logger= (Logger) LogManager.getLogger();

    @Test
    public void gradesRepoTest() throws IOException, SQLException {
        logger.traceEntry();
        properties.load(stream);
        GradeRepository repository=new GradeRepository(new GradeValidator(),properties,1l);
        Grade grade=repository.findOne(1l);
        assertTrue(grade.getGrade()==7.00);
        logger.info("Test FIND Ok");
        grade.setId(repository.getLastID());
        grade.setStudentID(3l);
        assertTrue(repository.save(grade)==null);
        logger.info("test SAVE Ok");
        grade.setUploaded(LocalDate.now());
        assertTrue(repository.update(grade)==null);
        logger.info("Test UPDATE Ok");
        assertTrue(repository.delete(grade.getId()).equals(grade));
        logger.info("Test DELETE Ok");
    }
}
