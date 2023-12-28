import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String input = scanner.nextLine().replaceAll("\\s", ""); // удалил введённые пробелы
//Создал блок исключения для вывода неправильного результата или ошибки
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
//начал метод калк для чтения введённого значения
     public static String calc(String input) {
        int operatorIndex = findOperatorIndex(input); //опознание арифметического знака

        if (operatorIndex == -1) {
            throw new IllegalArgumentException("Некорректное выражение");
        }
            //извлёк подстроки (символ) содержимого input на первую цифру, арифметический знак, вторая цифра
        String value1 = input.substring(0, operatorIndex);
        String operator = input.substring(operatorIndex, operatorIndex + 1);
        String value2 = input.substring(operatorIndex + 1);
        //ввёл переменные и тип для определения римской цифры
        int num1, num2;
        boolean isRomanInt = false;
//блок исключения для преобразования строки в int
        try {
            num1 = Integer.parseInt(value1);
            num2 = Integer.parseInt(value2);
        } catch (NumberFormatException e) {
            num1 = RomanToArabic(value1);
            num2 = RomanToArabic(value2);
            isRomanInt = true;
        }
//проверка ввода на соответствие параметрам от 1 до 10
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно");
        }
//добавил оператор свич для вычесления дествия при наличии конкретного символа арифметического выражения
        int result = switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new IllegalArgumentException("Неподдерживаемая операция: " + operator);
        };

         if (isRomanInt) {
            if (result <= 0) {
                throw new IllegalArgumentException("Римские числа не могут быть меньше единицы");
            }
            return ArabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }
//метод для нахождения арифметического знака
     static int findOperatorIndex(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                return i;
            }
        }
        return -1;
    }
//метод преобразования римских цифр в int для осуществления арифметических операций
     static int RomanToArabic(String roman) {
        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            if (!isValidRoman(roman.charAt(i))) {
                throw new IllegalArgumentException("Некорректное римское число: " + roman);
            }

            int currentNum = romanCharToArabic(roman.charAt(i)); //преобразование римских цифр для счёта
            int nextNum = (i + 1 < roman.length()) ? romanCharToArabic(roman.charAt(i + 1)) : 0; //преобразование следующего римского символа в число
            //при наличии выполняется операция суммы или вычитания из первого числа, если нет, присвается значение 0
            if (currentNum < nextNum) {
                result -= currentNum;
            } else {
                result += currentNum;
            }
        }

        return result;
    }
//метод проверки на сооответствие орфографии римских цифр
     static boolean isValidRoman(char romanChar) {
        return romanChar == 'I' || romanChar == 'V' || romanChar == 'X';
    }
 //метод перевода с римских цифр на арабксие
     static int romanCharToArabic(char romanChar) {
         return switch (romanChar) {
             case 'I' -> 1;
             case 'V' -> 5;
             case 'X' -> 10;
             default -> throw new IllegalArgumentException("Некорректный символ римской цифры: " + romanChar);
         };
    }
//метод перевода из арабского числа в римские
     static String ArabicToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Число должно быть положительным: " + number);
        }
//создал объёкт который выводит на конец строки римскую цифру
        StringBuilder roman = new StringBuilder();

        while (number >= 100) {
            roman.append("C");
            number -= 100;
        }

        if (number >= 90) {
            roman.append("XC");
            number -= 90;
        }

        while (number >= 50) {
            roman.append("L");
            number -= 50;
        }

        if (number >= 40) {
            roman.append("XL");
            number -= 40;
        }

        while (number >= 10) {
            roman.append("X");
            number -= 10;
        }

        if (number >= 9) {
            roman.append("IX");
            number -= 9;
        }

        while (number >= 5) {
            roman.append("V");
            number -= 5;
        }

        if (number >= 4) {
            roman.append("IV");
            number -= 4;
        }

        while (number > 0) {
            roman.append("I");
            number -= 1;
        }

        return roman.toString();
    }
}