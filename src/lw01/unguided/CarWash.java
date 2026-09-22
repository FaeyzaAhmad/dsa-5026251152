public class CarWash extends WashService {

    public CarWash(String id, int days, int units) {
        super(id, days, units);
    }

    @Override
    public int calculateCharge() {
        int days = getDays();
        int charge = 0;
        
        if (days <= 3) {
            charge = days * 35000;
        } else {
            charge = (3 * 35000) + ((days - 3) * 25000);
        }
        
        charge += 15000; 
        return charge;
    }

    @Override
    public String label() {
        return "Car";
    }
}