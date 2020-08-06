package ch.bfh.PieChart.models;

import ch.bfh.matrix.Matrix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SQLoperations {


    SQLDatabaseConnection sqlDatabaseConnection = new SQLDatabaseConnection();




    //we have only 7 products, which means productId is from 1 to 7;
    public int getValue(int productId){
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT VALUE FROM SalesValue WHERE ProductId ="+productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int value = 0;
            if (resultSet.next()){
                System.out.println(resultSet.getInt(1));
                value = resultSet.getInt(1);
            }
            return value;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }




    public ArrayList<Integer> getSalesValueTable(){
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SalesValue");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            System.out.println("ProductId"+"      "+"Value");
            while (resultSet.next()){
                System.out.print(resultSet.getInt(1));
                System.out.print("              ");
                System.out.println(resultSet.getInt(2));
                arrayList.add(resultSet.getInt(2));
            }
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




    public ArrayList<Integer> getValues(){
        try{
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT VALUE FROM SalesValue");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> valuesList = new ArrayList<>();
            while (resultSet.next()){
                valuesList.add(resultSet.getInt(1));
            }
            return valuesList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> getIds(){
        try{
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductId FROM SalesValue");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> idList = new ArrayList<>();
            while (resultSet.next()){
                idList.add(resultSet.getInt(1));
                System.out.println(resultSet.getInt(1));
            }
            return idList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void insertPieChartSlice() {
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PieChartCalcu pieChartCalcu = new PieChartCalcu();
            ArrayList<Double> startAngleList = pieChartCalcu.getStartAngleList();
            ArrayList<Double> endAngleList = pieChartCalcu.getEndAngleList();
            ArrayList<Integer> ids = getIds();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PieChartSlice (ProductId, StartAngle, EndAngle) VALUES (?, ?, ?)");

            for(int i = 0; i < ids.size();i++){
                preparedStatement.setInt(1, ids.get(i));
                preparedStatement.setDouble(2, startAngleList.get(i));
                preparedStatement.setDouble(3,endAngleList.get(i));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            System.out.println("Insert Completed");
        }
    }


    public void insertPieChartPos() {
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PieChartCalcu pieChartCalcu = new PieChartCalcu();
            ArrayList<Double> xList = pieChartCalcu.getXposList();
            ArrayList<Double> yList = pieChartCalcu.getYposList();
            ArrayList<Integer> posNum = pieChartCalcu.getPosNumber();
            ArrayList<Integer> ids = pieChartCalcu.getIds();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PieChartSlicePos (ProductId, Pos, X, Y) VALUES (?, ?, ?, ?)");

            for(int i = 0; i < ids.size();i++){
                preparedStatement.setInt(1, ids.get(i));
                preparedStatement.setInt(2, posNum.get(i));
                preparedStatement.setDouble(3,xList.get(i));
                preparedStatement.setDouble(4,yList.get(i));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            System.out.println("Insert Completed");
        }
    }



    public ArrayList<Double> getPieChartSlicePosTable(){
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PieChartSlicePos");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Double> arrayList = new ArrayList<Double>();
            System.out.println("ProductId"+"      "+"Pos"+"             "+"X"+"               "+"Y");
            while (resultSet.next()){
                System.out.print(resultSet.getInt(1));
                System.out.print("              ");
                System.out.print(resultSet.getInt(2));
                System.out.print("              ");
                System.out.print(resultSet.getDouble(3));
                System.out.print("         ");
                System.out.println(resultSet.getDouble(4));
                arrayList.add(resultSet.getDouble(2));
            }
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<Double> getTable(){
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PieChartSlice");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Double> arrayList = new ArrayList<Double>();
            System.out.println("ProductId"+"      "+"StartAngle"+"             "+"EndAngle");
            while (resultSet.next()){
                System.out.print(resultSet.getInt(1));
                System.out.print("              ");
                System.out.print(resultSet.getDouble(2));
                System.out.print("                   ");
                System.out.println(resultSet.getDouble(3));
                arrayList.add(resultSet.getDouble(2));
            }
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }





    public double getEndAngle(int productId){
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT EndAngle FROM PieChartSlice WHERE ProductId ="+productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            double endAngle = 0;
            if (resultSet.next()){
                System.out.println(resultSet.getDouble(1));
                endAngle = resultSet.getDouble(1);
            }
            return endAngle;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }



    public ArrayList<Double> getEndAngles(){
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT EndAngle FROM PieChartSlice");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Double> endAngleList = new ArrayList<>();
            while (resultSet.next()){
                endAngleList.add(resultSet.getDouble(1));
            }
            return endAngleList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public double getX(int productId, int pos){
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement statementForX = connection.prepareStatement("SELECT X FROM PieChartSlicePos WHERE ProductId ="+productId+"AND"+" Pos ="+pos);
            ResultSet resultSetForX = statementForX.executeQuery();
            double xValue = 0;
            if (resultSetForX.next()){
                xValue = resultSetForX.getDouble(1);
            }
            return xValue;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    public double getY(int productId, int pos){
        try {
            Connection connection = sqlDatabaseConnection.getConn();
            PreparedStatement statementForX = connection.prepareStatement("SELECT Y FROM PieChartSlicePos WHERE ProductId ="+productId+"AND"+" Pos ="+pos);
            ResultSet resultSetForX = statementForX.executeQuery();
            double yValue = 0;
            if (resultSetForX.next()){
                yValue = resultSetForX.getDouble(1);
            }
            return yValue;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

}
