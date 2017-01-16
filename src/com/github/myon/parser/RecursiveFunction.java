package com.github.myon.parser;

import java.util.function.Function;

public interface RecursiveFunction<F extends Function<?, ?>> extends Function<RecursiveFunction<F>, F> {

}
