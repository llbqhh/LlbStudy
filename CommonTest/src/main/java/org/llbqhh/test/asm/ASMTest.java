package org.llbqhh.test.asm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author lilibiao
 * @date 2020/1/19 3:43 下午
 */
public class ASMTest {
    @Test
    public void test() throws IOException {
        printHex();
    }

    private void printHex() throws IOException {
        ClassReader classReader = new ClassReader(ClassDemo.class.getName());
        classReader.accept(new ClassNode(), ClassReader.EXPAND_FRAMES);
        System.out.println(classReader.header);
        System.out.println(Arrays.toString(classReader.b));
        System.out.println(HexUtil.byte2Hex(classReader.b));
    }
}
