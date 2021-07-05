package de.yjulian.merly.tests;

import de.yjulian.merly.util.EnumUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnumUtilTest {

    @Test
    public void successTest() {
        Assertions.assertEquals(TestEnum.Opt1, EnumUtils.getOrDefault(TestEnum.class, "Opt1", TestEnum.Opt2));
    }

    @Test
    public void successTest2() {
        Assertions.assertEquals(TestEnum.Opt2, EnumUtils.getOrDefault(TestEnum.class, "Opt2", TestEnum.Opt1));
    }

    @Test
    public void defaultTest() {
        Assertions.assertEquals(TestEnum.Opt1, EnumUtils.getOrDefault(TestEnum.class, "Test", TestEnum.Opt1));
    }

    private enum TestEnum {
        Opt1,
        Opt2
    }

}
