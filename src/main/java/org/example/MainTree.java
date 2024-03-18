package org.example;

import org.example.model.Vehicle;
import org.example.service.SportCarService;

import java.util.Comparator;

public class MainTree {
    public static void main(String[] args) {
        Comparator<Vehicle> comparatorByPrice = Comparator.comparing(Vehicle::getPrice);
        Comparator<Vehicle> comparatorByModel = Comparator.comparing(Vehicle::getModel);
        Comparator<Vehicle> comparatorByCount = (o1, o2) -> Integer.compare(o2.getCount(), o1.getCount());
        Comparator<Vehicle> comparator = comparatorByPrice
                .reversed()
                .thenComparing(comparatorByModel)
                .thenComparing(comparatorByCount);
        Tree<Vehicle> tree = new Tree<>(comparator);
        SportCarService sportCarService = SportCarService.getInstance();
        for (int i = 0; i < 10; i++) {
            tree.add(sportCarService.createRandomVehicle());

        }
        tree.print();
        System.out.println("Sum of price left branch==>" + tree.getLeftTreePrice());
        System.out.println("Sum of price right branch==>" + tree.getRightTreePrice());
    }

}
