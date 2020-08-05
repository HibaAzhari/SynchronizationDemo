import java.util.*;
public class SalesReport {
    public static void main(String args[]) {
        Business Sprint = new Business();
        Outlet KlangValley = new Outlet(Sprint);
        
        Thread utama = new Thread(KlangValley);
        Thread sunway =	new	Thread(KlangValley);
        Thread KLCC = new Thread(KlangValley);
        Thread midvalley = new Thread(KlangValley);
        Thread pavilion = new Thread(KlangValley);
        Thread paradigm = new Thread(KlangValley);

        utama.start();
        sunway.start();
        KLCC.start();
        midvalley.start();
        pavilion.start();
        paradigm.start();

        while ((utama.isAlive()||sunway.isAlive()||KLCC.isAlive()||midvalley.isAlive()||pavilion.isAlive()||paradigm.isAlive()))	{ }
        System.out.println("----- Sprint's Monthly Reports -----");
        System.out.println("Total Sales: "+Sprint.getTotalSales());
        System.out.println(Business.products[0]+": "+Sprint.getProductSales()[0]);
        System.out.println(Business.products[1]+": "+Sprint.getProductSales()[1]);
        System.out.println(Business.products[2]+": "+Sprint.getProductSales()[2]);
        System.out.println("Total Revenue: "+Sprint.getRevenue());
    }
}
class Outlet implements Runnable {
    private Business business;

    public Outlet(Business business) {
        this.business = business;
    }
    public void run() {
        for (int i = 0; i < 10; i++) {
            business.sell(Business.products[0],2);
            business.sell(Business.products[1],2);
            business.sell(Business.products[2],2);
            business.returns(Business.products[0],1);
            business.returns(Business.products[1],1);
            business.returns(Business.products[2],1);
        }
    }
}
class Business {
    public Random rnd = new Random();
    public static final String[] products = new String[]{"T-shirts","Hoodies","Caps"};
    public static Map<String,Double> productPrices = new HashMap<String,Double>();
    
    private double totalRevenue;
    private int totalSales;
    private Map<String,Integer> productSales;

    public Business() {
        this.totalRevenue = 0.00;
        this.totalSales = 0;
        this.productSales = new HashMap<String,Integer>();
        productSales.put(products[0],0);
        productSales.put(products[1],0);
        productSales.put(products[2],0);
        
        productPrices.put(products[0],50.00);
        productPrices.put(products[1],100.00);
        productPrices.put(products[2],15.00);
    }
    

    public synchronized void sell(String product, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Double price = productPrices.get(product);
            totalRevenue += price;
            totalSales++;
            productSales.put(product,productSales.get(product)+1);
        }
    }

    public synchronized void returns(String product, int quantity) {
        for ( int i = 0; i < quantity; i++) {
            Double price = productPrices.get(product);
            totalRevenue -= price;
            totalSales--;
            productSales.put(product,productSales.get(product)-1);
        }
    }

    public double getRevenue() {
        return this.totalRevenue;
    }
    public int[] getProductSales() {
        return new int[]{productSales.get(products[0]),productSales.get(products[1]),productSales.get(products[2])};
    }
    public int getTotalSales() {
        return this.totalSales;
    }
}