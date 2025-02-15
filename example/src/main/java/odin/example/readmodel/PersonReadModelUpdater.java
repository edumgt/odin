
package odin.example.readmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import odin.applicationservices.ReadModelUpdater;
import odin.common.Message;
import odin.common.MessageDispatcher;
import odin.common.Result;
import odin.domainmodel.DomainEvent;
import odin.example.domain.events.PersonNameChanged;
import odin.example.domain.events.PersonRegistered;

@Service
public class PersonReadModelUpdater implements ReadModelUpdater<PersonReadModelRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonReadModelUpdater.class);
    private int numberOfPersonRegisteredReceived = 0;
    private int numberOfPersonNameChangedReceived = 0;

    public int getNumberOfPersonRegisteredReceived() {
        synchronized (this) {
            return numberOfPersonRegisteredReceived;
        }
    }

    public int getNumberOfPersonNameChangedReceived() {
        synchronized (this) {
            return numberOfPersonNameChangedReceived;
        }
    }

    @Autowired
    private PersonReadModelRepository personList;

    private Result handle(PersonRegistered personRegistered) {
        this.log(personRegistered);
        PersistableReadModelPerson person = new PersistableReadModelPerson(null,
                personRegistered.getMessageInfo().objectId(), personRegistered.getFirstName(),
                personRegistered.getLastName());
        personList.save(person);
        synchronized (this) {
            numberOfPersonRegisteredReceived++;
        }
        return null;
    }

    private Result handle(PersonNameChanged personNameChanged) {
        this.log(personNameChanged);

        PersistableReadModelPerson person = personList.findByIdentity(personNameChanged.getMessageInfo().objectId());
        person.setFirstName(personNameChanged.getFirstName());
        personList.save(person);
        synchronized (this) {
            numberOfPersonNameChangedReceived++;
        }
        return null;
    }

    @Override
    public Result handle(Message msg) {
        return new MessageDispatcher<Result>(null).match(PersonRegistered.class, this::handle, msg)
                .match(PersonNameChanged.class, this::handle, msg).result();
    }

    private void log(DomainEvent event) {
        LOGGER.info("DomainEvent {} received for aggregateId: {}", event.getClass().getSimpleName(),
                event.getMessageInfo().objectId());
    }

    @Override
    public PersonReadModelRepository getReadModelRepository() {
        return personList;
    }
}
