package odin.framework.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import odin.concepts.common.IMessageInfo;
import odin.concepts.common.Identity;
import odin.concepts.common.Version;

class MessageInfoTest {
    private static IMessageInfo sut;
    private static Identity aggregateId;
    private static Version aggregateVersion;

    @BeforeAll
    static void setUp() {
        aggregateId = new Identity();
        aggregateVersion = new Version();
        sut = new MessageInfo(new Identity(), LocalDateTime.now(), aggregateId, aggregateVersion);
    }

    @Test
    void getEventId() {
        assertNotNull(sut.messageId());
    }

    @Test
    void getSubjectId() {
        assertEquals(aggregateId, sut.subjectId());
        assertEquals(aggregateId.getId(), sut.subjectId().getId());
    }

    @Test
    void getSubjectVersion() {
        assertEquals(aggregateVersion, sut.subjectVersion());
    }

    @Test
    void getTimestamp() {
        assertNotNull(sut.timestamp());
    }
}