/* Copyright 2020 Peter Jansen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package odin.example.domain.state;

import java.util.UUID;

import odin.concepts.common.IMessageHandler;
import odin.framework.AbstractAggregateRoot;
import odin.example.domain.events.PersonNameChanged;
import odin.example.domain.events.PersonRegistered;

public class Person extends AbstractAggregateRoot {

    private String name;
    private String ssn;

    public String getSsn() {
        return ssn;
    }

    public String getName() {
        return name;
    }

    public Person(final UUID id) {
        super(id);
        this.name = null;
        this.ssn = null;
    }

    private Person registered(final PersonRegistered event) {     
        this.name = event.getName();
        this.ssn = event.getSsn();
        return this;
    }

    private Person changedName(final PersonNameChanged event) {
        this.name = event.getName();
        return this;
    }

    public void register(final String ssn, final String name) {
        this.applyEvent(new PersonRegistered(getId(), ssn, name));
    }

    public void changeName(final String name) {
        this.applyEvent(new PersonNameChanged(getId(), name));
    }

    @Override
    public <T> IMessageHandler dispatch(final T msg) {
        return match(PersonRegistered.class, (m) -> registered(m), msg)
                .match(PersonNameChanged.class, (p) -> changedName(p), msg);

    }
}