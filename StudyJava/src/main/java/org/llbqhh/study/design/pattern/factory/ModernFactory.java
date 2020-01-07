package org.llbqhh.study.design.pattern.factory;

/**
 * @author lilibiao
 * @date 2020/1/7 2:27 下午
 */
public class ModernFactory extends AbstractFactory {
    @Override
    Vehicle createVehicle() {
        return new Audi();
    }

    @Override
    Weapon createWeapon() {
        return new Gun();
    }
}
