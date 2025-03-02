package br.com.fiap.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Lists {

    public static <T> double sumDouble(Collection<T> list, Function<T, Double> supplier) {
        double amount = 0.0;
        for (T item : list) {
            Double value = supplier.apply(item);
            amount += value != null ? value : 0.0;
        }
        return amount;
    }
}
