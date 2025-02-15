
package odin.example.domain.state;

import odin.common.MessageDispatcher;
import odin.domainmodel.AggregateRoot;
import odin.domainmodel.Command;
import odin.domainmodel.DomainEvent;
import odin.example.domain.commands.ChangePersonName;
import odin.example.domain.commands.RegisterPerson;
import odin.example.domain.events.PersonNameChanged;
import odin.example.domain.events.PersonRegistered;

public class Person implements AggregateRoot {

    private String firstName;
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person() {
        this.firstName = null;
        this.lastName = null;
    }

    private Person registered(final PersonRegistered event) {
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        return this;
    }

    private Person changedName(final PersonNameChanged event) {
        this.firstName = event.getFirstName();
        return this;
    }

    public DomainEvent register(RegisterPerson command) {
        return new PersonRegistered(command.getMessageInfo().objectId(), command.getLastName(),
                command.getFirstName());
    }

    public DomainEvent changeName(ChangePersonName command) {
        return new PersonNameChanged(command.getMessageInfo().objectId(), command.getFirstName());
    }

    @Override
    public DomainEvent process(Command command) {
        var event = new MessageDispatcher<DomainEvent>(null).match(RegisterPerson.class, this::register, command)
                .match(ChangePersonName.class, this::changeName, command).result();
        source(event);
        return event;
    }

    @Override
    public AggregateRoot source(DomainEvent msg) {
        return new MessageDispatcher<>(this).match(PersonRegistered.class, this::registered, msg)
                .match(PersonNameChanged.class, this::changedName, msg).result();

    }
}
