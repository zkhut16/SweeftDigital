package pack;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.sun.activation.registries.LogSupport.log;


public class Main {
    static Boolean isPalindrome(String text){
        for(int i = 0; i < text.length()/2; i++){
            if(text.charAt(i) != text.charAt(text.length()-i-1))
                return false;
        }
        return true;
    }

    static int minSplit(int amount) throws Exception{
        if(amount<0)
            throw new Exception("Amount must be positive");
        int count = 0;
        count += amount/50;
        amount %= 50;
        count += amount/20;
        amount %= 20;
        count += amount/10;
        amount %= 10;
        count += amount/5;
        amount %= 5;
        count += amount;
        return count;
    }

    static int notContains(int[] array){
        int[] arr = new int[array.length+1];
        //copying array to not mutate passed argument
        for(int i = 0; i < arr.length-1; i++)
            arr[i] = array[i];
        arr[arr.length-1] = 0;
        Arrays.sort(arr);
        for(int i = 0; i < arr.length-1; i++){
            if(arr[i]>=0 && arr[i]!=arr[i+1] && arr[i]+1!=arr[i+1])
                return arr[i]+1;
        }
        if(arr[arr.length-1]<0)
            return 1;
        return arr[arr.length-1]+1;
    }

    static Boolean isProperly(String sequence){
        //For similar problems i'd use Stack but
        //in this problem this way is more eficient
        int count = 0;
        for(int i = 0; i < sequence.length(); i++){
            if(sequence.charAt(i) == '(')
                count++;
            if(sequence.charAt(i) == ')'){
                count--;
                if(count < 0)
                    return false;
            }
        }
        return (count == 0);
    }

    private static Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    static int countVariants(int stairsCount) {
        if(stairsCount < 0)
            return 0;
        if(stairsCount == 0)
            return 1;
        if(!map.containsKey(stairsCount)) {
            int count = countVariants(stairsCount - 1) + countVariants(stairsCount - 2);
            map.put(stairsCount, count);
        }
        return map.get(stairsCount);
    }

    private static Double exchangeRate(String from, String to){
        /*
        მომიწია თეიბლის მაგივრად ამომეღო სტრინგი რადგან HTML ში ეს თეიბლი იყო სპანში და აღქმული იყო ტექსტად
        შესაბამისად Jsoup ის სელექტორი ვერ წვდებოდა თეიბლს და ამიტომ მომიწია სტრინგად ამომეღო და სტრინგი დამეპარსა
        თეიბლი რომ ყოფილიყო ამოვიღებდი სტრიქონებს და შემდეგ თითოეული სტრიქონისთვის იგივეს ვიზამდი რაც სტრინგად ამოღებისას გავაკეთე
        */
        try {
            Document doc = Jsoup.connect("http://www.nbg.ge/rss.php").get();
            String br = doc.html();
            doc = Jsoup.parse(br, "", Parser.xmlParser());
            String table = doc.getElementsByTag("description").get(1).text();
            String[] rows = table.split("<tr>");
            Map<String, Double> rates = new HashMap<String, Double>();
            rates.put("GEL", 1.0);
            for(int i = 1; i < rows.length; i++) {
                String s = rows[i];
                String[] tds = s.split("<td>");
                String currency = tds[1].split("<")[0];
//                System.out.println("currency = " + currency);
                double rate = Double.parseDouble(tds[3].split("<")[0]);
//                System.out.println("rate = " + rate);
                int times = Integer.parseInt(tds[2].split("<")[0].split(" ")[0]);
//                System.out.println("times = " + times);
                rates.put(currency, rate/times);
            }
            return rates.get(from)/rates.get(to);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
//	check
    public static void main(String[] args) {

    }
}
