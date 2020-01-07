package org.llbqhh.study.design.pattern.factory;

/**
 * @author lilibiao
 * @date 2020/1/7 2:38 下午
 */
public class AncientFactory extends AbstractFactory {
    @Override
    Vehicle createVehicle() {
        return new Carriage();
    }

    @Override
    Weapon createWeapon() {
        return new Arrow();
    }
}
