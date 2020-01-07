package org.llbqhh.study.design.pattern.builder;

/**
 * @author lilibiao
 * @date 2020/1/7 8:40 下午
 */
public class BuilderMain {
    public static void main(String[] args) {
        Person person = new Person.PersonBuilder()
                .basicInfo(3, "zs", 25)
                .weight(77.7)
                .score(95)
                .location("北新中路", "49")
                .build();
        System.out.println(person.name);
    }
}
