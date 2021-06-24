package com.excilys.cdb.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {
	
	private Class<?> getEnumType(Class<?> targetType) {
		Class<?> enumType = targetType;
		while (enumType != null && !enumType.isEnum()) {
			enumType = enumType.getSuperclass();
		}
		Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
		return enumType;
	}

	@Override
	public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToEnum(getEnumType(targetType));
	}
	
	public class StringToEnum<T extends Enum> implements Converter<String, T> {

		private final Class<T> enumType;

		StringToEnum(Class<T> enumType) {
			this.enumType = enumType;
		}

		@Override
		@Nullable
		public T convert(String source) {
			if (source.isEmpty()) {
				return null;
			}
			return (T) Enum.valueOf(this.enumType, source.trim().toUpperCase());
		}

	}

}
