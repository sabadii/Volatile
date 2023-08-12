import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger beautyWords3 = new AtomicInteger(0);
    public static AtomicInteger beautyWords4 = new AtomicInteger(0);
    public static AtomicInteger beautyWords5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread tread3 = new Thread(() -> countBeautyWords(texts, 3));
        Thread tread4 = new Thread(() -> countBeautyWords(texts, 4));
        Thread tread5 = new Thread(() -> countBeautyWords(texts, 5));

        tread3.start();
        tread4.start();
        tread5.start();

        try {
            tread3.join();
            tread4.join();
            tread5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + beautyWords3.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + beautyWords4.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + beautyWords5.get() + " шт.");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();

    }

    public static void countBeautyWords(String[] texts, int length) {
        for (String text : texts) {
            if (isBeauty(text, length)) {
                if (length == 3) {
                    beautyWords3.incrementAndGet();
                } else if (length == 4) {
                    beautyWords4.incrementAndGet();
                } else if (length == 5) {
                    beautyWords5.incrementAndGet();
                }
            }
        }
    }

    public static boolean isBeauty(String text, int length) {
        if (text.length() != length) {
            return false;
        }

        for (int i = 0; i < length / 2; i++) {
            if (text.charAt(i) != text.charAt(length - i - 1)) {
                return false;
            }
        }

        for (int i = 1; i < length; i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}