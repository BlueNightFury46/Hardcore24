package uk.co.shadowtrilogy.hardcore24.json.groups;

public class json_location {
    public double x = 0.0;
    public double y = 0.0;
    public double z = 0.0;
    public String world = "world";
    public boolean hasBeenSet = false;

   public json_location(double X, double Y, double Z, String WORLD, boolean _hasBeenSet){
       x = X;
       y = Y;
       z = Z;
       world = WORLD;
       hasBeenSet = _hasBeenSet;
   }
}
