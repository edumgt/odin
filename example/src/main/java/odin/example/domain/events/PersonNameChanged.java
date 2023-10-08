/* Copyright 2019 Peter Jansen
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

package odin.example.domain.events;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import odin.concepts.common.Identity;
import odin.concepts.common.MessageInfo;
import odin.concepts.domainmodel.DomainEvent;
import odin.framework.common.MessageInfoRecord;

@NoArgsConstructor
public class PersonNameChanged implements DomainEvent {

    private static final long serialVersionUID = 1L;
    private MessageInfoRecord messageInfo;
    private String firstName;

    public PersonNameChanged(Identity aggregateId, String firstName) {
        messageInfo = new MessageInfoRecord(new Identity(), LocalDateTime.now(), aggregateId, null);
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public MessageInfo getMessageInfo() {
        return messageInfo;
    }
}
