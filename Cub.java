import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Cub{
	
	double SCALE = 4;
	
    double POV = 500;
    double Offset = 0;
    Polygon T = new Polygon();
    double[] Px, Py, Pz, PPx, PPy; 
   
    int    V1temp, V2temp, V3temp;                      //temp variables used in sorting
    double V4temp, V5temp;                              //temp variables used in sorting
    double oldX, oldY, oldZ;                            //temp variables used in rotating

    int[] V1 = {0, 0, 4, 4, 7, 7, 3, 3, 2, 2, 3, 3,};   //vertex1
    int[] V2 = {3, 2, 0, 1, 4, 5, 7, 6, 6, 5, 0, 4,};   //vertex2
    int[] V3 = {2, 1, 1, 5, 5, 6, 6, 2, 5, 1, 4, 7,};   //vertex3
    
    double[] V4 = new double[12];                       //Average Z of all 3 vertices 
    double[] V5 = new double[12];                       //DotProduct of Normal and POV

    double CPX1, CPX2, CPX3, CPY1, CPY2, CPY3;          //temp variables used in Cross Product
    double CPZ1, CPZ2, CPZ3, DPX, DPY, DPZ;             //temp variables used in Cross/Dot Product
    
    double W, H, D;
    double x, y, z;
    
    Color fill, contur;
    
    
    public Cub(double x, double y, double z, double W, double H, double D, Color fill, Color contur) {
    	this.W = W;
    	this.H = H;
    	this.D = D;
    	this.x = x;
    	this.y = y;
    	this.z = z;
    	this.fill = fill;
    	this.contur = contur;
    	calc_points();
    }
    
    public void scale(double diff) {
    	this.W = W/diff;
    	this.H = H/diff;
    	this.D = D/diff;
    	calc_points();
    }
    
    public void scale_pitic(double diff) {
    	this.H = H/diff;
    	calc_points();
    }
    
    public void calc_points() {
    	Px  = new double[]{-W+x, -W+x,  W+x,  W+x, -W+x, -W+x, W+x,  W+x};     //real x-coordinate, 8 points
    	Py  = new double[]{-H+y,  H+y,  H+y, -H+y, -H+y,  H+y, H+y, -H+y};     //real y-coordinate, 8 points
    	Pz  = new double[]{-D+z, -D+z, -D+z, -D+z,  D+z,  D+z, D+z,  D+z};     //real z-coordinate, 8 points
    	PPx = new double[]{-W, -W,  W,  W, -W, -W, W,  W};     //projected x-coordinate, 8 points
    	PPy = new double[]{-H,  H,  H, -H, -H,  H, H, -H};     //projected y-coordinate, 8 points
    }
    
    public void SortByDepth(Graphics screen) {
       for (int i = 0; i < 12 ; i++) {
          V4[i] = (Pz[V1[i]]+Pz[V2[i]]+Pz[V3[i]]) / 3;
       }
       for (int g = 0; g < 11 ; g++) {
          for (int h = 0; h < 12; h++) {
             if (V4[g] < V4[h]) {
                V1temp = V1[g]; V2temp = V2[g]; V3temp = V3[g]; V4temp = V4[g]; V5temp = V5[g];
                V1[g]=V1[h];    V2[g]=V2[h];    V3[g]=V3[h];    V4[g]=V4[h];    V5[g]=V5[h];
                V1[h]=V1temp;   V2[h]=V2temp;   V3[h]=V3temp;   V4[h]=V4temp;   V5[h]=V5temp;
             }
          }
       }
    }

    public void BackFaceCulling(Graphics screen) {
       for (int i = 0; i < 12 ; i++) {        
          // Cross Product
          CPX1 = Px[V2[i]] - Px[V1[i]];
          CPY1 = Py[V2[i]] - Py[V1[i]];
          CPZ1 = Pz[V2[i]] - Pz[V1[i]];
          CPX2 = Px[V3[i]] - Px[V1[i]];
          CPY2 = Py[V3[i]] - Py[V1[i]];
          CPZ2 = Pz[V3[i]] - Pz[V1[i]];
          DPX = CPY1 * CPZ2 - CPY2 * CPZ1;
          DPY = CPX2 * CPZ1 - CPX1 * CPZ2;
          DPZ = CPX1 * CPY2 - CPX2 * CPY1;
          // DotProduct uses POV vector 0,0,POV  as x1,y1,z1
          V5[i] = 0 * DPX + 0 * DPY + POV * DPZ;
       }
    }

    public void ApplyProjection(Graphics screen) {
       for (int i = 0; i < 8 ; i++) {
          PPx[i] = Px[i];
          PPy[i] = Py[i];
       }
    }

    public void drawCube(Graphics screen) {
       for (int i = 0; i < 12 ; i++) {
    	   screen.setColor(fill);
          if (V5[i] > 0) {              
             T.addPoint ((int)(PPx[V1[i]]+Offset), (int)(PPy[V1[i]]+Offset));
             T.addPoint ((int)(PPx[V2[i]]+Offset), (int)(PPy[V2[i]]+Offset));
             T.addPoint ((int)(PPx[V3[i]]+Offset), (int)(PPy[V3[i]]+Offset));
             screen.fillPolygon(T);
             screen.setColor(contur);
             screen.drawPolygon(T);
             
             T.reset();
          }
       }
    }
    
    public void rotate_by_x(double Theta) {
    	rotate_by_x(Theta, x, y, z);
    }
    
    public void rotate_by_y(double Theta) {
    	rotate_by_y(Theta, x, y, z);
    }
    
    public void rotate_by_z(double Theta) {
    	rotate_by_z(Theta, x, y, z);
    }
    
    public void rotate_by_x(double Theta, double x, double y, double z) {
    	 for (int i = 0; i < 8; i++) {
             oldY = Py[i] - y; oldZ = Pz[i] - z;
             Py[i] = oldY * Math.cos(Theta) - oldZ * Math.sin(Theta) + y;  //rotate about X
             Pz[i] = oldY * Math.sin(Theta) + oldZ * Math.cos(Theta) + z;  //rotate about X
    	 }
    }

    public void rotate_by_y(double Theta, double x, double y, double z) {
    	for (int i = 0; i < 8; i++) {
    	 oldX = Px[i] - x; oldZ = Pz[i] - z;
         Px[i] = oldZ * Math.sin(Theta) + oldX * Math.cos(Theta) + x;  //rotate about Y
         Pz[i] = oldZ * Math.cos(Theta) - oldX * Math.sin(Theta) + z;  //rotate about Y
    	}
    }
    
    public void rotate_by_z(double Theta, double x, double y, double z) {
    	for (int i = 0; i < 8; i++) {
    	 oldX = Px[i] - x; oldY = Py[i] - y;
         Px[i] = oldX * Math.cos(Theta) - oldY * Math.sin(Theta) + x;  //rotate about Z
         Py[i] = oldX * Math.sin(Theta) + oldY * Math.cos(Theta) + y;  //rotate about Z
    	}    	 

    }
    
    public void RotatePoints() {
       //rotate_by_x();
       //rotate_by_y(0.005);
       //rotate_by_z();
    }

    public void paint(Graphics screen) {
        SortByDepth(screen);
        BackFaceCulling(screen);
        ApplyProjection(screen);
        drawCube(screen);
       // RotatePoints();
    }

	public void translate(double dx, double dy, double dz) {
		for (int i = 0; i < 8; i++) {
			Px[i] += dx;
			Py[i] += dy;
			Pz[i] += dz;
		}
	}
}