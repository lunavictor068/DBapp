package DBapp;


public class CodeEvalTest {

    public static void main(String[] args){
        String sample = "a a a b 5";


        String[] array = sample.split(" ");
        int mth = Integer.parseInt(array[array.length-1]);
        if (mth < array.length)
            System.out.println(array[array.length - mth - 1]);

    }

//    private static String findMth(String string){
//        String[] array = string.split(" ");
//        int mth = Integer.parseInt(array[array.length]);
//        if (mth <= array.length-1)
//            System.out.println(array[array.length - mth]);
//        return arrayLetters[arrayLetters.length-mth];
//    }
//
//    private static int getInt(String string){
//        return Integer.parseInt(Character.toString(string.charAt(string.length()-1)));
//    }
}
