package de.yjulian.merly.tests;

import de.yjulian.merly.database.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnumFakeTest {

    @Test
    public void test1() {
        Assertions.assertEquals("MALFUNCTION", Collection.MALFUNCTION.name());
    }

}
