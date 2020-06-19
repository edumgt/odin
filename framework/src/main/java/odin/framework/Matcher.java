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

package odin.framework;

import odin.concepts.common.IMessageAction;
import odin.concepts.common.IMessageHandler;

public class Matcher {
    final IMessageHandler noMatchHandler;

    public Matcher(IMessageHandler noMatchHandler) {
        this.noMatchHandler = noMatchHandler;
    }

    public <T, Y> Matcher match(final Class<T> msgClazz, final IMessageAction<T> msgAction, final Y msg) {
        if (msgClazz.isInstance(msg)) {
            return new Matcher(msgAction.executeAction(msgClazz.cast(msg)));
        }
        return this;
    }

    public IMessageHandler result() {
        return noMatchHandler;
    }
}