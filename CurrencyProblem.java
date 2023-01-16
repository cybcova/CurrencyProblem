import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyProblem {
   public static void main(String[] args) {
      System.out.println("523 YEN -> CAD");
      System.out.println("R: " + convertCurrency("YEN", "CAD", 523d));
      System.out.println("6.06 CAD -> YEN");
      System.out.println("R: " + convertCurrency("CAD", "YEN", 6.06d));
      System.out.println("1 YEN -> LLL");
      System.out.println("R: " + convertCurrency("YEN", "LLL", 1d));
      System.out.println("100 USD -> AUD");
      System.out.println("R: " + convertCurrency("USD", "AUD", 100d));
      System.out.println("129 AUD -> USD");
      System.out.println("R: " + convertCurrency("AUD", "USD", 129d));
      System.out.println("----------");
      System.out.println("100 YEN -> CAD");
      System.out.println("R: " + convertCurrency("YEN", "CAD", 100d));
      System.out.println("6.06 CAD -> YEN");
      System.out.println("R: " + convertCurrency("CAD", "YEN", 6.06d));
      System.out.println("----------");
      System.out.println("100 CAD -> GBP");
      System.out.println("R: " + convertCurrency("CAD", "GBP", 100d));
      System.out.println("58 GBP -> CAD");
      System.out.println("R: " + convertCurrency("GBP", "CAD", 58d));
      System.out.println("100 GBP -> CAD");
      System.out.println("R: " + convertCurrency("GBP", "CAD", 100d));
      System.out.println("173.0 CAD -> GBP");
      System.out.println("R: " + convertCurrency("CAD", "GBP", 173.0d));
      // System.out.println("----------");
      // System.out.println("100 USD -> MXN");
      // System.out.println("R: " + convertCurrency("USD", "MXN", 100d));
      // System.out.println("1957 MXN -> USD");
      // System.out.println("R: " + convertCurrency("MXN", "USD", 1957.0d));
      // System.out.println("100 MXN -> USD");
      // System.out.println("R: " + convertCurrency("MXN", "USD", 100.0d));
      // System.out.println("5.1 USD -> MXN");
      // System.out.println("R: " + convertCurrency("USD", "MXN", 5.1d));
   }

   static double convertCurrency (String curr, String target, double value) {
      List<CurrencyExchange> examplesExchanges = new ArrayList<>();
      
      // examplesExchanges.add(new CurrencyExchange("USD","MXN",19.57d));
      // examplesExchanges.add(new CurrencyExchange("MXN","USD",0.051d));

      examplesExchanges.add(new CurrencyExchange("USD","AUD",1.29d));
      examplesExchanges.add(new CurrencyExchange("USD","GBP",.72d));
      examplesExchanges.add(new CurrencyExchange("USD","EUR",.83d));
      examplesExchanges.add(new CurrencyExchange("AUD","USD",.77d));
      examplesExchanges.add(new CurrencyExchange("YEN","USD",.0093d));
      examplesExchanges.add(new CurrencyExchange("CAD","GBP",.58d));
      examplesExchanges.add(new CurrencyExchange("GBP","CAD",1.73d));

      List<List<CurrencyExchange>> exchangePath = new ArrayList<>();
      List<String> fromList = new ArrayList<>();
      fromList.add(curr);
      List<String> fromSharedList = new ArrayList<>();
      CurrencyExchange currencyTarget = null;
      do{

         List<CurrencyExchange> posibleExchanges = examplesExchanges
         .stream()
         .filter(c -> fromList.contains(c.getFrom()))
         .collect(Collectors.toList());

         if(posibleExchanges.isEmpty()) return 0;

         exchangePath.add(posibleExchanges);

         fromSharedList.addAll(fromList);
         fromList.clear();
         fromList.addAll(
            posibleExchanges
            .stream()
            .filter(c-> !fromSharedList.contains(c.getTo()))
            .map(e->e.getTo())
            .collect(Collectors.toList())
            );

         currencyTarget = findTo(posibleExchanges, target);
         
      }while(currencyTarget==null);

      Double totalExchange=1d;
      String lastTo= currencyTarget.getTo();
      do{
         CurrencyExchange lastCurrencyExchange = findTo(exchangePath.remove(exchangePath.size()-1), lastTo);

         lastTo= lastCurrencyExchange.getFrom();

         totalExchange *= lastCurrencyExchange.getValueExchange();
      }while(!exchangePath.isEmpty());

      return Math.round(value * totalExchange * 100.0) / 100.0;
   }

   static CurrencyExchange findTo(List<CurrencyExchange> lExchanges, String to){
      return lExchanges
      .stream()
      .filter(c -> to.equals(c.getTo()))
      .findFirst()
      .orElse(null);
   }

   static class CurrencyExchange{
      private String from;
      private String to;
      private double valueExchange;

      CurrencyExchange(String from, String to, double valueExchange){
         this.from = from;
         this.to = to;
         this.valueExchange = valueExchange;
      }

      public void setFrom(String from){
         this.from = from;
      }
      public void setTo(String to){
         this.to = to;
      }
      public void setValueExchange(double valueExchange){
         this.valueExchange = valueExchange;
      }

      public String getFrom(){
         return from;
      }
      public String getTo(){
         return to;
      }
      public double getValueExchange(){
         return valueExchange;
      }

      @Override
      public String toString() {
         return "CurrencyExchange [from=" + from + ", to=" + to + ", valueExchange=" + valueExchange + "]";
      }
   }
}

