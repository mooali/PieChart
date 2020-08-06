package ch.bfh.PieChart.models;

import ch.bfh.matrix.Matrix;

import java.util.ArrayList;

public class PieChartCalcu {

    final double PI = Math.PI;
    final double MITTELPUNKT = 1200;
    final int RADUIS = 1000;
    final double UMFANG = 2 * RADUIS * PI;
    SQLoperations sqLoperations = new SQLoperations();


    public ArrayList<Double> getEndAngleList(){
        int totalValu = 0;
        double endAngle= 0.0;
        ArrayList<Double> endAngleList = new ArrayList<>();
        ArrayList<Integer> values = sqLoperations.getValues();
        for(int i : values){
            totalValu+=i;
        }
        for(int i : values){
            endAngle += (UMFANG/totalValu)*i/1000;
            endAngleList.add(endAngle);
        }
        return endAngleList;
    }

    public ArrayList<Double> getStartAngleList(){
        ArrayList<Double> startAngleList = new ArrayList<>();
        double startAngle = 0.0;
        startAngleList.add(startAngle);
        for(int i = 0; i<getEndAngleList().size()-1; i++){
            startAngle = getEndAngleList().get(i);
            startAngleList.add(startAngle);
        }
        return startAngleList;
    }



    public ArrayList<Double> getXposList(){
        ArrayList<Double> xList = new ArrayList<>();
        ArrayList<Double> endAngleList = sqLoperations.getEndAngles();
        xList.add(MITTELPUNKT);
        for(double i : endAngleList){
            xList.add(MITTELPUNKT);
            double x = RADUIS*Math.sin(i)+MITTELPUNKT;
            xList.add(x);
        }
        xList.add(4,xList.get(2));
        fillArray(xList);
        return xList;
    }


    public ArrayList<Double> getYposList(){
        ArrayList<Double> yList = new ArrayList<>();
        ArrayList<Double> endAngleList = sqLoperations.getEndAngles();
        yList.add(MITTELPUNKT);
        boolean helper = true;
        for(double i : endAngleList){
            if(helper){
                yList.add(MITTELPUNKT+RADUIS);
            }else {
                yList.add(MITTELPUNKT);
            }
            double x = RADUIS*Math.cos(i)+MITTELPUNKT;
            yList.add(x);
            helper = false;

        }
        yList.add(4,yList.get(2));
        fillArray(yList);
        return yList;
    }



    public void fillArray(ArrayList<Double> arrayList){
        int i = 5;
        int index = 7;
        while(i < arrayList.size()){
            if(index<arrayList.size()&&i<arrayList.size()) {
                arrayList.add(index, arrayList.get(i));
            }
            index+=3;
            i+=3;
        }
    }



    public ArrayList<Integer> getPosNumber(){
        ArrayList<Integer> pos = new ArrayList<>();
        int endLoop = getXposList().size();
        int j = 0;
        int k = 0;
        while (j<endLoop){
            if(k==3){
                k=0;
            }
            pos.add(k);
            j++;
            k++;
        }
        return pos;
    }

    public ArrayList<Integer> getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        int endLoop = getXposList().size();
        int id = 1;
        int k = 0;
        int i = 0;
        while (i<endLoop){
            if(k==3){
                id++;
                k=0;
            }
            ids.add(id);
            i++;
            k++;
        }
        return ids;
    }

    public Matrix getPointMatrix(int productId){
        ArrayList<Double> pointValuesX = new ArrayList<>();
        ArrayList<Double> pointValuesY = new ArrayList<>();
        SQLoperations sqLoperations = new SQLoperations();
        int i = 0;
        while (i<3){
            pointValuesX.add(sqLoperations.getX(productId,i));
            pointValuesY.add(sqLoperations.getY(productId,i));
            i++;
        }
        System.out.println(pointValuesX);
        double[][] pointValues = new double[][]{
                {pointValuesX.get(0),pointValuesY.get(0)},
                {pointValuesX.get(1),pointValuesY.get(1)},
                {pointValuesX.get(2),pointValuesY.get(2)}
        };
        Matrix matrix = new Matrix(pointValues);

        return matrix;
    }




}
