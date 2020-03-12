package Services;

import Entity.Assignment;
import Repository.TemaRepository;
import Validator.TemaValidator;

import java.time.LocalDate;
import java.util.stream.StreamSupport;

public class TemeService extends EntityService<Assignment> {
    public TemeService(Long disciplina) {
        this.repository = new TemaRepository(new TemaValidator(), disciplina);
    }

    public Assignment getCurrent() {
        return StreamSupport.stream(findAll().spliterator(), false).filter(x -> x.getStartWeek().isBefore(LocalDate.now()) && x.getEndWeek().isAfter(LocalDate.now())).findFirst().orElse(null);
    }

}
