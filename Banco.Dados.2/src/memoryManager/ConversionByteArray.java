package memoryManager;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionByteArray {
	public final static int		MASK	= 0xff;
	public final static byte	EMPTY	= 0;
	public final static byte	FULL	= 1;

	public static byte[] convertIntegerToByteArray(int fieldInteger) {
		byte[] result = new byte[4];

		for (int i = 0; i < 4; i++) {
			int offset = (result.length - 1 - i) * 8;
			result[i] = (byte) ((fieldInteger >>> offset) & MASK);
		}

		return result;
	}

	public static int convertByteArraytoInteger(byte[] byteArray) {
		return (byteArray[0] << 24) + ((byteArray[1] & 0xFF) << 16) + ((byteArray[2] & 0xFF) << 8) + (byteArray[3] & 0xFF);
	}

	public static byte[] convertLongToByteArray(long fieldLong) {
		byte[] result = new byte[8];

		for (int i = 0; i < 8; i++) {
			int offset = (result.length - 1 - i) * 8;
			result[i] = (byte) ((fieldLong >>> offset) & MASK);
		}

		return result;
	}

	public static long convertByteArraytoLong(byte[] byteArray) {
		long fieldLong = 0;

		for (int i = 0; i < 8; i++) {
			fieldLong <<= 8;
			fieldLong ^= (long) byteArray[i] & 0xFF;
		}
		return fieldLong;
	}

	public static byte[] convertStringToByteArray(String fieldString, int sizeString) {
		byte[] result = new byte[sizeString];

		for (int i = 0; i < sizeString; i++) {

			if (fieldString.length() > i)
				result[i] = fieldString.getBytes()[i];
			else result[i] = EMPTY;
		}

		return result;
	}

	public static String convertByteArrayToString(byte[] byteArray, int size) {
		return new String(byteArray).trim();
	}

	public static byte[] convertBoolToByteArray(Boolean fieldBool) {
		byte[] result = new byte[1];

		result[0] = 0;

		if (fieldBool)
			result[0] = 1;

		return result;
	}

	public static boolean convertByteArrayToBool(byte[] byteArray) {
		if (byteArray[0] == 1)
			return true;
		else return false;
	}

	public static byte[] convertFloatToByteArray(float fieldFloat) {
		int i = Float.floatToRawIntBits(fieldFloat);

		return convertIntegerToByteArray(i);
	}

	public static float convertByteArrayToFloat(byte[] byteArray) {
		int bits = 0;
		int i = 0;

		for (int shifter = 3; shifter >= 0; shifter--) {
			bits |= ((int) byteArray[i] & MASK) << (shifter * 8);
			i++;
		}

		return Float.intBitsToFloat(bits);
	}

	public static byte[] convertDateTimeToByteArray(Date fieldDateTime) {
		long fieldLong = fieldDateTime.getTime();

		return ConversionByteArray.convertLongToByteArray(fieldLong);
	}

	public static String convertByteArraytoDateTime(byte[] data) {
		long longField = ConversionByteArray.convertByteArraytoLong(data);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Date dateField = new Date(longField);
		
		return df.format(dateField);
	}

	public static byte[] convertFieldToByteArray(Field field, String fieldValue) throws Exception {
		try {
			switch (field.getType()) {
			case BOOL:
				if (fieldValue.equalsIgnoreCase("true") || fieldValue.equalsIgnoreCase("1")) {
					return ConversionByteArray.convertBoolToByteArray(true);

				} else if (fieldValue.equalsIgnoreCase("false") || fieldValue.equalsIgnoreCase("0")) {
					return ConversionByteArray.convertBoolToByteArray(false);
				}
			case BYTE:
				byte[] byteArray = new byte[0];
				byteArray[0] = Byte.parseByte(fieldValue);
				;

				return byteArray;

			case DATETIME:
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date fieldDateTime = df.parse(fieldValue);

				return ConversionByteArray.convertDateTimeToByteArray(fieldDateTime);

			case FLOAT:
				if (fieldValue == null || fieldValue.isEmpty())
					fieldValue = "0.0";
				
				float fieldFloat = Float.parseFloat(fieldValue);
				return ConversionByteArray.convertFloatToByteArray(fieldFloat);

			case INTEGER:
				if (fieldValue == null || fieldValue.isEmpty())
					fieldValue = "0";
				
				int fieldInt = Integer.valueOf(fieldValue);
				return ConversionByteArray.convertIntegerToByteArray(fieldInt);

			case STRING:
				if (fieldValue.length() > field.getSize() / 2) //REVER
//				if (fieldValue.length() > field.getSize())
					throw new Exception("Tamanho do valor maior que o tamanho permitido");

				return ConversionByteArray.convertStringToByteArray(fieldValue, field.getSize());

			}
		} catch (Exception e) {
			throw new Exception("Valor informado " + fieldValue + " (" + field.getName() + "), nao eh um " + field.getType() + " valido.\nErro: " + e.getMessage());
		}

		return null;
	}

	public static String convertByArrayByType(TypeField typeField, byte[] data) {
		switch (typeField) {
		case BOOL:
			if (ConversionByteArray.convertByteArrayToBool(data))
				return "true";
			else 
				return "false";

		case BYTE:
			return String.valueOf(data[0]);

		case DATETIME:
			return ConversionByteArray.convertByteArraytoDateTime(data);

		case FLOAT:
			return String.valueOf(ConversionByteArray.convertByteArrayToFloat(data));

		case INTEGER:
			return String.valueOf(ConversionByteArray.convertByteArraytoInteger(data));

		case STRING:
			return ConversionByteArray.convertByteArrayToString(data, data.length);
		}

		return "";
	}

	public static byte[] getByteArrayFromBuffer(final ByteBuffer buffer, int init, int size) {
		byte[] data = new byte[size];

		buffer.rewind();

		for (int i = 0; i < size; i++) {
			if (buffer.get(init + i) != ConversionByteArray.EMPTY)
				data[i] = buffer.get(init + i);
		}

		return data;
	}

	public static void clearBuffeInField(ByteBuffer buffer, int init, int size) {
		for (int i = 0; i < size; i++) {
			buffer.put(init + i, ConversionByteArray.EMPTY);
		}
	}

}
