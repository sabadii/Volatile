import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger length3 = new AtomicInteger(0);
    public static AtomicInteger length4 = new AtomicInteger(0);
    public static AtomicInteger length5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrom = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrom(text, texts.length)) {
                    lengthCounter(texts);
                }
            }
        });

        Thread sameLetters = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    lengthCounter(texts);
                }
            }
        });

        Thread orderedLetters = new Thread(() -> {
            for (String text : texts) {
                if (isOrdered(text, texts.length)) {
                    lengthCounter(texts);
                }
            }
        });

        palindrom.start();
        sameLetters.start();
        orderedLetters.start();

        try {
            palindrom.join();
            sameLetters.join();
            orderedLetters.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + length3.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + length4.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + length5.get() + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void lengthCounter(String[] texts) {
        for (String text : texts) {
            if (text.length() == 3) length3.incrementAndGet();
            else if (text.length() == 4) length4.incrementAndGet();
            else if (text.length() == 5) length5.incrementAndGet();
        }
    }

    public static boolean isPalindrom(String text, int length) {
        for (int i = 0; i < length / 2; i++) {
            if (text.charAt(i) != text.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSameLetter(String text) {
        return text.chars().allMatch(c -> c == text.charAt(0));
    }

    public static boolean isOrdered(String text, int length) {
        for (int i = 1; i < length; i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

}
