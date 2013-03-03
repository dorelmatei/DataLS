package com.datals.foundation.model.base;

public interface Executor<T> {

	Result<T> execute(Context context);
}
