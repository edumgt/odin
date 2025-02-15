
package odin.common;

import java.time.LocalDateTime;

public record MessageInfoRecord(Identity messageId, LocalDateTime timestamp, Identity objectId, Version objectVersion)
        implements MessageInfo {
}
